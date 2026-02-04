package com.ramp.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficeAddressReq {
    private Boolean sameAsFactory = false;
    private AddressReq address;
}
