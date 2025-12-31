package com.ramp.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndustrialUnitInfo {
    @NotBlank
    private String name;
    
    @NotBlank
    private String location;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "addressLine", column = @Column(name = "unit_address_line")),
        @AttributeOverride(name = "po", column = @Column(name = "unit_po")),
        @AttributeOverride(name = "district", column = @Column(name = "unit_district")),
        @AttributeOverride(name = "state", column = @Column(name = "unit_state"))
    })
    private Address address;
    
    @NotBlank
    private String phone;
    
    @Email
    @NotBlank
    private String email;
}