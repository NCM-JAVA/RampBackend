package com.ramp.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardFilterDTO {

    private String year;
    private String intervention;
    private String district;
}