package com.ramp.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.*;
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentMatrix {

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "managerial", column = @Column(name = "apst_managerial")),
        @AttributeOverride(name = "supervisory", column = @Column(name = "apst_supervisory")),
        @AttributeOverride(name = "skilled", column = @Column(name = "apst_skilled")),
        @AttributeOverride(name = "semiSkilled", column = @Column(name = "apst_semi_skilled")),
        @AttributeOverride(name = "unskilled", column = @Column(name = "apst_unskilled")),
        @AttributeOverride(name = "others", column = @Column(name = "apst_others"))
    })
    private EmploymentCount apst;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "managerial", column = @Column(name = "non_apst_managerial")),
        @AttributeOverride(name = "supervisory", column = @Column(name = "non_apst_supervisory")),
        @AttributeOverride(name = "skilled", column = @Column(name = "non_apst_skilled")),
        @AttributeOverride(name = "semiSkilled", column = @Column(name = "non_apst_semi_skilled")),
        @AttributeOverride(name = "unskilled", column = @Column(name = "non_apst_unskilled")),
        @AttributeOverride(name = "others", column = @Column(name = "non_apst_others"))
    })
    private EmploymentCount nonApst;

    // ✅ NULL-SAFE total
    public Integer getTotalEmployees() {
        return safeTotal(apst) + safeTotal(nonApst);
    }

    private int safeTotal(EmploymentCount count) {
        return count == null ? 0 : count.getTotal();
    }
}
