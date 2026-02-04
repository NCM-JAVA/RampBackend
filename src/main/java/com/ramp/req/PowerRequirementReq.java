package com.ramp.req;

import com.ramp.enums.PowerUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PowerRequirementReq {
    private Double powerValue;
    private PowerUnit unit;
    private String loadSanctionCertificatePath;
}
