package com.ramp.req;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionCapacityReq {
    @NotBlank(message = "Product name is required")
    private String product;

    private Double quantity;
    private Double value;
}
