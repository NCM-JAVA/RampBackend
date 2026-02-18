package com.ramp.res;

import java.time.LocalDateTime;

import com.ramp.entity.IndustrialUnitRegistration;
import com.ramp.enums.ApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndustrialUnitRegistrationResponse {
    private String id;
    private String userId;
    private String unitName;
    private String unitLocation;
    private String constitutionType;
    private String industryType;
    private ApplicationStatus status;
    private Integer currentStep;
    private Double totalInvestment;
    private Integer totalEmployees;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime submittedAt;
    private String rejectionReason;
    private String reviewedBy;
    private LocalDateTime reviewedAt;

    // Full entity for detailed view
    private IndustrialUnitRegistration details;

    public static IndustrialUnitRegistrationResponse fromEntity(IndustrialUnitRegistration entity) {
        return IndustrialUnitRegistrationResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .unitName(entity.getUnitDetails() != null ? entity.getUnitDetails().getName() : null)
                .unitLocation(entity.getUnitDetails() != null ? entity.getUnitDetails().getLocation() : null)
                .constitutionType(entity.getConstitutionType() != null ? entity.getConstitutionType().name() : null)
                .industryType(entity.getIndustryType() != null ? entity.getIndustryType().name() : null)
                .status(entity.getStatus())
                .currentStep(entity.getCurrentStep())
                .totalInvestment(entity.getFinancials() != null ? entity.getFinancials().getTotal() : 0.0)
                .totalEmployees(entity.getEmployment() != null ? entity.getEmployment().getTotalEmployees() : 0)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .submittedAt(entity.getSubmittedAt())
                .rejectionReason(entity.getRejectionReason())
                .reviewedBy(entity.getReviewedBy())
                .reviewedAt(entity.getReviewedAt())
                .build();
    }

    public static IndustrialUnitRegistrationResponse fromEntityWithDetails(IndustrialUnitRegistration entity) {
        IndustrialUnitRegistrationResponse response = fromEntity(entity);
        response.setDetails(entity);
        return response;
    }
}
