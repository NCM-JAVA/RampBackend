package com.ramp.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "kpis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kpi {

    @Id
    @Column(name = "kpi_no")
    private String kpiNo;

    private String kpiName;

    @ManyToOne
    @JoinColumn(name = "activity_no")
    private Activity activity;
}