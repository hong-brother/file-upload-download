package com.hsnam.spring.file.service.imple;

import com.hsnam.spring.file.model.FileModel;
import com.hsnam.spring.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImple implements FileService {

    @Value("${file.upload}")
    private String baseDir;

    private static Path uploadDir;

    private static Path tempDir;

    @PostConstruct
    private void init(){
        try{
            log.info("init Service");
            log.info("File Upload BASE Dir = {}", baseDir);
            uploadDir = Paths.get(baseDir + File.separator + "upload");
            log.info("Upload Dir ={}", uploadDir.toString());
            tempDir = Paths.get(baseDir + File.separator + "temp");
            log.info("temp Dir ={}", tempDir.toString());
            if(!Files.isDirectory(uploadDir)){
                log.info("Create Upload Dir");
                Files.createDirectories(uploadDir);
            }
            if(!Files.isDirectory(tempDir)){
                log.info("Create Temp Dir");
                Files.createDirectories(tempDir);
            }
        }catch (IOException io){
            log.info("error={}",io.getMessage());
        }
    }//end of init

    /**
     *
     * @param file
     * @param scope
     * @return
     * @throws IOException
     */
    @Override
    public FileModel store(MultipartFile file, String scope)  throws IOException{
        String fileName = UUID.randomUUID().toString();
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileOriName = FilenameUtils.getBaseName(file.getOriginalFilename());
        FileModel fileModel = FileModel.builder()
                .scope(scope)
                .fileName(fileName)
                .filePath(tempDir+File.separator+scope)
                .originFileName(fileOriName)
                .extension(fileExtension)
                .fileSize(file.getSize()).build();
        log.info("File = {}", fileModel.toString());

        Path targetPath = Paths.get(fileModel.getFilePath()).resolve(fileName+"."+fileExtension);
        if(!targetPath.toFile().isDirectory()){
            targetPath.toFile().mkdirs();
        }
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        return fileModel;
    }

    @Override
    public List<FileModel> storeList(MultipartFile[] files, String scope) throws IOException{
        List<FileModel> fileModelList =null;
        for(int i=0; i<files.length; i++){

//          store(files[i], scope)
        }
        return null;
    }//end of storeList

    @Override
    public void moveTempToUpload(String fileName, String scope) throws IOException {
        Path targetFile = tempDir.resolve(scope).resolve(fileName);
        Path moveFile = uploadDir.resolve(scope).resolve(fileName);
        if(targetFile.toFile().isFile()){
            if(moveFile.toFile().isDirectory())
                moveFile.toFile().mkdirs();

            Files.move(targetFile, moveFile);
        }else {
            throw new IOException("File Not Found");
        }
    }

    @Override
    public void deleteFile(FileModel fileModel) {

    }

    @Override
    public void deleteFileList(List<FileModel> fileModelList) {

    }
}
