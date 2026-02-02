package com.ramp.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramp.dto.IndustrialUnitListDTO;
import com.ramp.entity.IndustrialUnitRegistration;
import com.ramp.enums.ApplicationStatus;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;

import com.ramp.entity.IndustrialUnitRegistration;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface IndustrialUnitRegistrationRepository extends JpaRepository<IndustrialUnitRegistration, Long>,JpaSpecificationExecutor<IndustrialUnitRegistration> {

    long countByStatus(ApplicationStatus status);

    long countByUserId(String userId);

    long countByUserIdAndStatus(String userId, ApplicationStatus status);

    List<IndustrialUnitRegistration> findByStatus(ApplicationStatus status);

    @Modifying
    @Transactional
    @Query(
        "update IndustrialUnitRegistration r " +
        "set r.status = :status, " +
        "    r.updatedAt = CURRENT_TIMESTAMP, " +
        "    r.submittedAt = " +
        "        case " +
        "            when :status = 'SUBMITTED' then CURRENT_TIMESTAMP " +
        "            else r.submittedAt " +
        "        end " +
        "where r.id = :id"
    )
    int updateStatus(
            @Param("id") Long id,
            @Param("status") ApplicationStatus status
    );

    
}
