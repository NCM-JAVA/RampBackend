package com.ramp.entity;

import javax.persistence.Embeddable;

import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentCount {
    private Integer managerial = 0;
    private Integer supervisory = 0;
    private Integer skilled = 0;
    private Integer semiSkilled = 0;
    private Integer unskilled = 0;
    private Integer others = 0;
    
    public Integer getTotal() {
        return managerial + supervisory + skilled + semiSkilled + unskilled + others;
    }
}