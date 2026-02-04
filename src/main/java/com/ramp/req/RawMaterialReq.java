package com.ramp.req;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialReq {
    @NotBlank(message = "Material name is required")
    private String materialName;
}
