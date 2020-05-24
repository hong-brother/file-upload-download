package com.hsnam.spring.file.web;

import com.hsnam.spring.file.model.BoardModel;
import com.hsnam.spring.file.model.FileModel;
import com.hsnam.spring.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequestMapping("/api/v1/file")
@RestController
public class FileUploadDownloadController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/{scope}/upload/" , method = RequestMethod.POST)
    public ResponseEntity addploadFile(@RequestBody MultipartFile file, @PathVariable("scope") String scope){
        try{
            log.info(file.getName());
            FileModel fileModel = fileService.store(file, scope);
            return ResponseEntity.ok(fileModel);
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/{scope}/upload-list" , method = RequestMethod.POST)
    public ResponseEntity addUploadFileList(@RequestParam("file") MultipartFile[] file, @PathVariable("scope") String scope){
        try{
            log.info("file length ={}",file.length);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }//end of uploadFileList

    @PostMapping("/insert/{scope}")
    public ResponseEntity addFileInfo(@PathVariable("scope") String scope, @RequestParam("file")String fileName){
        try{
            fileService.moveTempToUpload(fileName,scope);
            return ResponseEntity.ok().build();
        }catch (IOException io){
            log.info(io.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }//end of addFileInfo

    @DeleteMapping("/{scope}/delete")
    public ResponseEntity deleteFile(@PathVariable("scope") String scope, @RequestParam("file") String fileId){
        return ResponseEntity.ok().build();
    }//end of deleteFile

    @DeleteMapping("/{scipe}/delete-list")
    public ResponseEntity deleteFileList(@PathVariable("scope") String scope, @RequestParam("file") List<String> fileId){
        return ResponseEntity.ok().build();
    }

}
