package com.ramp.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;

import com.ramp.enums.ApplicationStatus;
import com.ramp.enums.ConstitutionType;
import com.ramp.enums.IndustryType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "industrial_unit_registrations")
@Data
@NoArgsConstructor
public class IndustrialUnitRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    private String userId;
    
    // Step 1: Unit Details
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "unit_name")),
        @AttributeOverride(name = "location", column = @Column(name = "unit_location"))
    })
    private IndustrialUnitInfo unitDetails;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "sameAsFactory", column = @Column(name = "office_same_as_factory"))
    })
    private OfficeAddress officeAddress;
    
    // Step 2: Constitution
    @Enumerated(EnumType.STRING)
    private ConstitutionType constitutionType;
    
    @OneToMany(mappedBy = "registration", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartnerDirector> partnersDirectors = new ArrayList<>();
    
    // Step 3: Operational Plan
    private LocalDate commencementDate;
    
    @Enumerated(EnumType.STRING)
    private IndustryType industryType;
    
    private String activityType;
    private String productServiceName;
    
    @Embedded
    private PowerRequirement powerRequirement;
    
    @OneToMany(mappedBy = "registration", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductionCapacity> productionCapacities = new ArrayList<>();

    @OneToMany(mappedBy = "registration", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RawMaterial> rawMaterials = new ArrayList<>();
    
    // Step 4: Legal Details
    @Embedded
    private LegalRegistrations legalDetails;
    
    // Step 5: Financials
    @Embedded
    private FixedCapitalInvestment financials;
    
    // Step 6: Employment
    @Embedded
    private EmploymentMatrix employment;
    
    // Step 7: Declaration
    @Embedded
    private Declaration declaration;
    
    // Meta
    private Integer currentStep = 0;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.DRAFT;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    private LocalDateTime submittedAt;

    // Review fields
    private String rejectionReason;
    private String reviewedBy;
    private LocalDateTime reviewedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
