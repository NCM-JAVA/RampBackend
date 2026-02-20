package com.ramp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "raw_materials")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank
    private String materialName;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "registration_id")
    private IndustrialUnitRegistration registration;
}