package com.ramp.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FixedCapitalInvestmentReq {
    private Double land = 0.0;
    private Double siteDevelopment = 0.0;
    private Double factoryBuilding = 0.0;
    private Double officeBuilding = 0.0;
    private Double plantMachinery = 0.0;
    private Double electricalInstallation = 0.0;
    private Double preopExpenses = 0.0;
    private Double miscAssets = 0.0;
}
