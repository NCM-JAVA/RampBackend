package com.ramp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ramp.entity.IndustrialUnitRegistration;

public interface IndustrialUnitRegistrationRepository
        extends JpaRepository<IndustrialUnitRegistration, Long> {
}
