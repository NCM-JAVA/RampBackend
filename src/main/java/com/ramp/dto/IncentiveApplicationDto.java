package com.ramp.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncentiveApplicationDto {

    private String id;
    private String userId;

    // Unit Details
    @NotBlank(message = "Unit name is required")
    private String unitName;

    private String gstin;
    private String udhyamNumber;
    private Double investmentAmount;
    private Integer employmentCommitment;

    // Factory Address
    private String factoryLocation;
    private String factoryDistrict;
    private String factoryState;
    private String factoryPoliceStation;
    private String factoryMobile;
    private String factoryEmail;

    // Office Address
    private String officeLocation;
    private String officeDistrict;
    private String officeState;
    private String officePoliceStation;
    private String officeMobile;
    private String officeEmail;

    // Status
    private String status;
    private Boolean declarationAccepted;

    // Documents (read-only in response)
    private List<ApplicationDocumentDto> documents;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
