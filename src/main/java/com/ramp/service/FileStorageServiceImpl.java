package com.ramp.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ramp.config.FileStorageConfig;
import com.ramp.utils.FileStorageException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final FileStorageConfig config;

    @Override
    public String storeFile(MultipartFile file, String userId) {
        if (file.isEmpty()) {
            throw new FileStorageException("Cannot store empty file");
        }

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Validate file extension
        String extension = getFileExtension(originalFileName);
        if (!config.isExtensionAllowed(extension)) {
            throw new FileStorageException("File extension '" + extension + "' is not allowed. " +
                    "Allowed extensions: " + config.getAllowedExtensions());
        }

        // Validate file size
        if (file.getSize() > config.getMaxFileSize()) {
            throw new FileStorageException("File size exceeds maximum allowed size of " +
                    (config.getMaxFileSize() / 1024 / 1024) + " MB");
        }

        // Generate unique file name
        String uniqueFileName = UUID.randomUUID().toString() + "_" + originalFileName;

        try {
            // Create user directory if it doesn't exist
            Path userDir = Paths.get(config.getUploadDir(), userId).toAbsolutePath().normalize();
            Files.createDirectories(userDir);

            // Copy file to target location
            Path targetLocation = userDir.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Return relative path for storage in database
            return userId + "/" + uniqueFileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + originalFileName, ex);
        }
    }

    @Override
    public Resource loadFileAsResource(String userId, String fileName) {
        try {
            Path filePath = Paths.get(config.getUploadDir(), userId, fileName).toAbsolutePath().normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new FileStorageException("File not found: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileStorageException("File not found: " + fileName, ex);
        }
    }

    @Override
    public void deleteFile(String userId, String fileName) {
        try {
            Path filePath = Paths.get(config.getUploadDir(), userId, fileName).toAbsolutePath().normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            throw new FileStorageException("Could not delete file: " + fileName, ex);
        }
    }

    @Override
    public boolean fileExists(String userId, String fileName) {
        Path filePath = Paths.get(config.getUploadDir(), userId, fileName).toAbsolutePath().normalize();
        return Files.exists(filePath);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
}
