package com.ramp.entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "interventions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Intervention {

    @Id
    @Column(name = "intervention_no")
    private String interventionNo;

    private String intervention;

   
}