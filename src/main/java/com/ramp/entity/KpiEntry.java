package com.ramp.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "kpi_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KpiEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Financial year is required")
    private String financialYear;

    @NotBlank(message = "Quarter is required")
    private String quarter;

    @NotBlank(message = "Intervention is required")
    private String intervention;

    @NotBlank(message = "Component is required")
    private String component;

    @NotBlank(message = "KPI is required")
    private String kpi;

    @NotBlank(message = "Activity is required")
    private String activity;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

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
