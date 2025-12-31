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
@Table(name = "production_capacities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionCapacity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank
    private String product;
    
    private Double quantity;
    private Double value;
    
    @ManyToOne
    @JoinColumn(name = "registration_id")
    private IndustrialUnitRegistration registration;
}