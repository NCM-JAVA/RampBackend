package com.ramp.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String storeFile(MultipartFile file, String userId);

    Resource loadFileAsResource(String userId, String fileName);

    void deleteFile(String userId, String fileName);

    boolean fileExists(String userId, String fileName);
}
