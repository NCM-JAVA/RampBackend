package com.ramp.entity;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.ramp.enums.PowerUnit;

import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PowerRequirement {
    private Double powerValue;

    @Enumerated(EnumType.STRING)
    private PowerUnit unit;

    private String loadSanctionCertificate;
    private String loadSanctionCertificatePath;
}
