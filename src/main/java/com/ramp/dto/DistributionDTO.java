package com.ramp.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistributionDTO {

    private String name;   // Component Name
    private Long value;    // Count
}