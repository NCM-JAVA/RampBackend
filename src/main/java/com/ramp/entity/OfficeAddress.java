package com.ramp.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfficeAddress {
    private Boolean sameAsFactory = false;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "addressLine", column = @Column(name = "office_address_line")),
        @AttributeOverride(name = "po", column = @Column(name = "office_po")),
        @AttributeOverride(name = "district", column = @Column(name = "office_district")),
        @AttributeOverride(name = "state", column = @Column(name = "office_state"))
    })
    private Address address;
}
