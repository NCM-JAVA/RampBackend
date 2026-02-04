package com.ramp.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LegalDetailsReq {
    private String udyamIemNo;
    private String gstNo;
    private String tradingLicenseNo;
    private String factoryLicenseNo;
    private String spcbConsent;
    private String otherRegistration;

    // Document paths
    private String udyamIemDocPath;
    private String gstDocPath;
    private String factoryLicenseDocPath;
    private String pollutionBoardConsentDocPath;
}
