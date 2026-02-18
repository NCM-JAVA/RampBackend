package com.ramp.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "district_kpi_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistrictKpiEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "District name is required")
    private String districtName;

    @NotNull(message = "SC count is required")
    private Integer sc;

    @NotNull(message = "ST count is required")
    private Integer st;

    @NotNull(message = "OBC count is required")
    private Integer obc;

    @NotNull(message = "General category count is required")
    private Integer generalCategory;

    @NotNull(message = "Women count is required")
    private Integer women;

    private Integer total;

    @NotBlank(message = "Financial year is required")
    private String financialYear;

    @NotBlank(message = "Quarter is required")
    private String quarter;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        calculateTotal();
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        calculateTotal();
        updatedAt = LocalDateTime.now();
    }

    public void calculateTotal() {
        this.total = (sc != null ? sc : 0)
                + (st != null ? st : 0)
                + (obc != null ? obc : 0)
                + (generalCategory != null ? generalCategory : 0)
                + (women != null ? women : 0);
    }
}
