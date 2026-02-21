package com.ramp.dto;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardDropdownDTO {

    private List<String> financialYears;
    private List<String> interventions;
    private List<String> districts;
}