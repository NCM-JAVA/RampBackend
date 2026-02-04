package com.ramp.dto;

import java.time.LocalDateTime;

import com.ramp.enums.ApplicationStatus;
import com.ramp.enums.ConstitutionType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IndustrialUnitListDTO {

    private Long unit;
    private String unitName;
    private String unitLocation;
    private ConstitutionType constitutionType;
    private String gstNo;
    private String udyamIemNo;
    private LocalDateTime createdAt;
    private ApplicationStatus status;
}
