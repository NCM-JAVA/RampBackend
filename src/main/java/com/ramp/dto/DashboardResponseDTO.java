package com.ramp.dto;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponseDTO {

    private Long totalBeneficiaries;
    private Long msmesBenefited;
    private Long jobsSupported;
    private Long eventsConducted;
    private SocialCompositionDTO socialComposition;
    private List<DistributionDTO> distribution;   // Pie chart
    private List<DistrictDTO> districtWiseData;   // Bar chart
}