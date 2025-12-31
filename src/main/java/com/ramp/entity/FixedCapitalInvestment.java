package com.ramp.entity;

import javax.persistence.Embeddable;

import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FixedCapitalInvestment {
    private Double land = 0.0;
    private Double siteDevelopment = 0.0;
    private Double factoryBuilding = 0.0;
    private Double officeBuilding = 0.0;
    private Double plantMachinery = 0.0;
    private Double electricalInstallation = 0.0;
    private Double preopExpenses = 0.0;
    private Double miscAssets = 0.0;
    
    public Double getTotal() {
        return land + siteDevelopment + factoryBuilding + officeBuilding +
               plantMachinery + electricalInstallation + preopExpenses + miscAssets;
    }
}