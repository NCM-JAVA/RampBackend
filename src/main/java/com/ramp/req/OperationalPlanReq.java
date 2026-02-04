package com.ramp.req;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import com.ramp.enums.IndustryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationalPlanReq {
    private LocalDate commencementDate;

    @NotNull(message = "Industry type is required")
    private IndustryType industryType;

    private String activityType;
    private String productServiceName;

    @Valid
    private PowerRequirementReq powerRequirement;

    @Valid
    private List<ProductionCapacityReq> productionCapacities;

    @Valid
    private List<RawMaterialReq> rawMaterials;
}
