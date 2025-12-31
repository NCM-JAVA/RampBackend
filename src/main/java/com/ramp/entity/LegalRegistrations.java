package com.ramp.entity;

import javax.persistence.Embeddable;

import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LegalRegistrations {
    private String udyamIemNo;
    private String gstNo;
    private String tradingLicenseNo;
    private String factoryLicenseNo;
    private String spcbConsent;
    private String otherRegistration;
}
