package com.ramp.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ramp.entity.IndustrialUnitRegistration;
import com.ramp.enums.ApplicationStatus;

public interface IndustrialUnitRegistrationRepository
        extends JpaRepository<IndustrialUnitRegistration, Long> {

    List<IndustrialUnitRegistration> findByUserIdOrderByCreatedAtDesc(String userId);

    List<IndustrialUnitRegistration> findByStatusOrderByCreatedAtDesc(ApplicationStatus status);

    List<IndustrialUnitRegistration> findAllByOrderByCreatedAtDesc();

    long countByStatus(ApplicationStatus status);
}
