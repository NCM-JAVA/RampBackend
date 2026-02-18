package com.ramp.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDocumentDto {

    private Long id;
    private String applicationId;
    private String documentType;
    private String fileName;
    private String filePath;
    private LocalDateTime uploadedAt;
}
