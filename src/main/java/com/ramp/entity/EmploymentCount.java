package com.ramp.entity;

import javax.persistence.Embeddable;

import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentCount {
    private Integer managerial ;
    private Integer supervisory;
    private Integer skilled ;
    private Integer semiSkilled;
    private Integer unskilled ;
    private Integer others ;
    
    public Integer getTotal() {
        return managerial + supervisory + skilled + semiSkilled + unskilled + others;
    }
}



