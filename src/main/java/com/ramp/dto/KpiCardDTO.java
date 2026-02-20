package com.ramp.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KpiCardDTO {

    private String title;
    private Long value;
    private String icon;
    private String color;
}