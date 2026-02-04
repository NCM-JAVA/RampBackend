package com.ramp.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse {
    private String fileName;
    private String filePath;
    private String fileType;
    private long size;
    private String message;

    public FileUploadResponse(String fileName, String filePath, String fileType, long size) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.size = size;
        this.message = "File uploaded successfully";
    }
}
