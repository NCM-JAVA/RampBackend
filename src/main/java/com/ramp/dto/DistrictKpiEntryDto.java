package com.ramp.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistrictKpiEntryDto {

    private Long id;

    @NotBlank(message = "District name is required")
    private String districtName;

    @NotNull(message = "SC count is required")
    private Integer sc;

    @NotNull(message = "ST count is required")
    private Integer st;

    @NotNull(message = "OBC count is required")
    private Integer obc;

    @NotNull(message = "General category count is required")
    private Integer generalCategory;

    @NotNull(message = "Women count is required")
    private Integer women;

    private Integer total;

    @NotBlank(message = "Financial year is required")
    private String financialYear;

    @NotBlank(message = "Quarter is required")
    private String quarter;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
