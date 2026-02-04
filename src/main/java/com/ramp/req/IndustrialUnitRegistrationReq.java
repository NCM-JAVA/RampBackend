package com.ramp.req;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndustrialUnitRegistrationReq {

    // Step 1: Unit Details
    @NotNull(message = "Unit details are required")
    @Valid
    private UnitDetailsReq unitDetails;

    // Step 2: Constitution
    @NotNull(message = "Constitution details are required")
    @Valid
    private ConstitutionReq constitution;

    // Step 3: Operational Plan
    @NotNull(message = "Operational plan is required")
    @Valid
    private OperationalPlanReq operationalPlan;

    // Step 4: Legal Details
    @Valid
    private LegalDetailsReq legalDetails;

    // Step 5: Financials
    @Valid
    private FixedCapitalInvestmentReq financials;

    // Step 6: Employment
    @Valid
    private EmploymentReq employment;

    // Step 7: Declaration
    @NotNull(message = "Declaration is required")
    @Valid
    private DeclarationReq declaration;
}
