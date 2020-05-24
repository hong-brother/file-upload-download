package com.hsnam.spring.file.service;

import com.hsnam.spring.file.model.FileModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    /**
     * File 저장
     * @param file
     * @return
     */
    FileModel store(MultipartFile file, String scope) throws IOException;

    /**
     * 다중 파일 저장
     * @param files
     * @return
     */
    List<FileModel> storeList(MultipartFile[] files, String scope) throws IOException;

    /**
     * 파일 이동
     * @param fileName
     * @throws IOException
     */
    void moveTempToUpload(String fileName, String scope) throws IOException;

    /**
     * 파일 삭제
     * @param fileModel
     */
    void deleteFile(FileModel fileModel);

    /**
     * 다중 파일 삭제
     * @param fileModelList
     */
    void deleteFileList(List<FileModel> fileModelList);
}
