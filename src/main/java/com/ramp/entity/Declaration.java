package com.ramp.entity;

import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Declaration {
    private Boolean isDeclared = false;

    @NotBlank
    private String fullName;

    @Lob
    private String signature; // Base64 encoded

    @Lob
    private String seal;

    // File paths for uploaded documents
    private String signatureFilePath;
    private String sealFilePath;
}