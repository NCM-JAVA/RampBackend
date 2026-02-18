package com.ramp.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateTotalDto {

    private Long totalSc;
    private Long totalSt;
    private Long totalObc;
    private Long totalGeneralCategory;
    private Long totalWomen;
    private Long grandTotal;
}
