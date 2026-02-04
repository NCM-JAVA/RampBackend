package com.ramp.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentCountReq {
    private Integer managerial = 0;
    private Integer supervisory = 0;
    private Integer skilled = 0;
    private Integer semiSkilled = 0;
    private Integer unskilled = 0;
    private Integer others = 0;
}
