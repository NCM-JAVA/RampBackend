package com.ramp.utils;

import java.time.LocalDateTime;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.ramp.entity.IndustrialUnitRegistration;
import com.ramp.enums.ApplicationStatus;

public class IndustrialUnitSpecification {

    public static Specification<IndustrialUnitRegistration> withFilters(
            String unit,
            String unitName,
            String unitLocation,
            ApplicationStatus status,
            LocalDateTime fromDate,
            LocalDateTime toDate) {

        return (root, query, cb) -> {

            Predicate predicate = cb.conjunction();

            if (unit != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("id"), unit));
            }

            if (unitName != null && !unitName.trim().isEmpty()) {
                predicate = cb.and(predicate,
                        cb.like(
                                cb.lower(root.get("unitDetails").get("name")),
                                "%" + unitName.toLowerCase() + "%"
                        ));
            }

            if (unitLocation != null && !unitLocation.trim().isEmpty()) {
                predicate = cb.and(predicate,
                        cb.like(
                                cb.lower(root.get("unitDetails").get("location")),
                                "%" + unitLocation.toLowerCase() + "%"
                        ));
            }

            if (status != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("status"), status));
            }

            if (fromDate != null) {
                predicate = cb.and(predicate,
                        cb.greaterThanOrEqualTo(root.get("createdAt"), fromDate));
            }

            if (toDate != null) {
                predicate = cb.and(predicate,
                        cb.lessThanOrEqualTo(root.get("createdAt"), toDate));
            }

            return predicate;
        };
    }
    
    public static Specification<IndustrialUnitRegistration> forSingleRecord(
            String id,
            String unitName,
            String unitLocation,
            String gstNo,
            String udyamIemNo,
            ApplicationStatus status) {

        return (root, query, cb) -> {

            Predicate predicate = cb.conjunction();

            // 🔑 PRIMARY KEY (MANDATORY)
            predicate = cb.and(predicate,
                    cb.equal(root.get("id"), id));

            if (unitName != null && !unitName.isEmpty()) {
                predicate = cb.and(predicate,
                        cb.equal(
                            cb.lower(root.get("unitDetails").get("name")),
                            unitName.toLowerCase()
                        ));
            }

            if (unitLocation != null && !unitLocation.isEmpty()) {
                predicate = cb.and(predicate,
                        cb.equal(
                            cb.lower(root.get("unitDetails").get("location")),
                            unitLocation.toLowerCase()
                        ));
            }

            if (gstNo != null && !gstNo.isEmpty()) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("legalDetails").get("gstNo"), gstNo));
            }

            if (udyamIemNo != null && !udyamIemNo.isEmpty()) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("legalDetails").get("udyamIemNo"), udyamIemNo));
            }

            if (status != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("status"), status));
            }

            return predicate;
        };
    }


}
