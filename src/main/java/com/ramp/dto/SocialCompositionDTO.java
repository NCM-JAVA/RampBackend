package com.ramp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialCompositionDTO {

    private Long total;

    private Long sc;
    private Long st;
    private Long obc;
    private Long general;
    private Long women;
}