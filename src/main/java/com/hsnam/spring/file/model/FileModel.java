package com.hsnam.spring.file.model;

import lombok.Builder;
import lombok.Data;

import java.io.File;

@Builder
@Data
public class FileModel {
    private String fileName;
    private String originFileName;
    private String filePath;
    private String extension;
    private String scope;
    private long fileSize;
    private String fileFullPath;

    public String getFileFullPath() {
        return filePath + File.separator + fileName + "." + extension;
    }
}
