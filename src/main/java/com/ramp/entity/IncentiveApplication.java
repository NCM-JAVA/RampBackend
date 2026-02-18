package com.ramp.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "incentive_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncentiveApplication {

    @Id
    private String id;

    @Column(nullable = false)
    private String userId;

    // ========== Unit Details ==========
    @NotBlank(message = "Unit name is required")
    private String unitName;

    private String gstin;

    private String udhyamNumber;

    private Double investmentAmount;

    private Integer employmentCommitment;

    // ========== Factory Address ==========
    private String factoryLocation;
    private String factoryDistrict;
    private String factoryState;
    private String factoryPoliceStation;
    private String factoryMobile;
    private String factoryEmail;

    // ========== Office Address ==========
    private String officeLocation;
    private String officeDistrict;
    private String officeState;
    private String officePoliceStation;
    private String officeMobile;
    private String officeEmail;

    // ========== Status & Declaration ==========
    private String status;

    private Boolean declarationAccepted;

    // ========== Documents ==========
    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ApplicationDocument> documents = new ArrayList<>();

    // ========== Timestamps ==========
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = "DRAFT";
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
