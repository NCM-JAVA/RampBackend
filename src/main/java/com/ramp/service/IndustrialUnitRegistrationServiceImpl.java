package com.ramp.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ramp.entity.IndustrialUnitRegistration;
import com.ramp.repo.IndustrialUnitRegistrationRepository;
import com.ramp.service.IndustrialUnitRegistrationService;

import lombok.RequiredArgsConstructor;
@Service
@Transactional
public class IndustrialUnitRegistrationServiceImpl
        implements IndustrialUnitRegistrationService {

    @Autowired
    private IndustrialUnitRegistrationRepository repository;

    @Override
    public IndustrialUnitRegistration create(IndustrialUnitRegistration registration) {

        System.out.println(">>> SERVICE CALLED <<<");
        IndustrialUnitRegistration saved = repository.save(registration);
        System.out.println(">>> SAVED ID = " + saved.getId());

        return saved;
    }
}
