package com.ramp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "partners_directors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDirector {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    private String aadharDocPath;
    private String panDocPath;

    @ManyToOne
    @JoinColumn(name = "registration_id")
    private IndustrialUnitRegistration registration;
}