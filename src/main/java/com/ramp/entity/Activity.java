package com.ramp.entity;

import lombok.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity {

    @Id
    @Column(name = "activity_no")
    private String activityNo;

    private String activities;

    @ManyToOne
    @JoinColumn(name = "component_no")
    @JsonIgnore

    private Component component;
}