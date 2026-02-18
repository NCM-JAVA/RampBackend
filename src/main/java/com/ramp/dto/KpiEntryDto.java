package com.ramp.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KpiEntryDto {

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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
