package com.ramp.entity;

import lombok.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "components")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Component {

    @Id
    @Column(name = "component_no")
    private String componentNo;

    private String component;

    @ManyToOne
    @JoinColumn(name = "intervention_no")
    @JsonIgnore
    private Intervention intervention;

//    @OneToMany(mappedBy = "component", cascade = CascadeType.ALL)
//    private List<Activity> activities;
}