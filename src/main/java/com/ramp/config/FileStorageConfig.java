package com.ramp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;

@Configuration
@Getter
public class FileStorageConfig {

    @Value("${file.storage.upload-dir:./uploads/industrial-unit}")
    private String uploadDir;

    @Value("${file.storage.allowed-extensions:pdf,jpg,jpeg,png}")
    private String allowedExtensions;

    @Value("${file.storage.max-file-size:10485760}")
    private long maxFileSize;

    public String[] getAllowedExtensionsArray() {
        return allowedExtensions.split(",");
    }

    public boolean isExtensionAllowed(String extension) {
        if (extension == null) return false;
        String ext = extension.toLowerCase();
        for (String allowed : getAllowedExtensionsArray()) {
            if (allowed.trim().equalsIgnoreCase(ext)) {
                return true;
            }
        }
        return false;
    }
}
