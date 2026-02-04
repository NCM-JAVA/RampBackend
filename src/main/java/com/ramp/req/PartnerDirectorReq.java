package com.ramp.req;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDirectorReq {
    @NotBlank(message = "Partner/Director name is required")
    private String name;

    @NotBlank(message = "Partner/Director address is required")
    private String address;

    private String aadharDocPath;
    private String panDocPath;
}
