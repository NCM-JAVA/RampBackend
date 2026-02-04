package com.ramp.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeclarationReq {
    @NotNull(message = "Declaration must be accepted")
    private Boolean isDeclared;

    @NotBlank(message = "Full name is required")
    private String fullName;

    private String signatureFilePath;
    private String sealFilePath;
}
