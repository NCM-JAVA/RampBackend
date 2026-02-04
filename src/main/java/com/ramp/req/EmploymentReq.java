package com.ramp.req;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentReq {
    @Valid
    private EmploymentCountReq apst;

    @Valid
    private EmploymentCountReq nonApst;
}
