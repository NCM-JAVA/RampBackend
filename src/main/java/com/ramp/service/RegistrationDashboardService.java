package com.ramp.service;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ramp.enums.ApplicationStatus;
import com.ramp.repo.IndustrialUnitRegistrationRepository;
import com.ramp.dto.IndustrialUnitListDTO;
import com.ramp.entity.IndustrialUnitRegistration;
import com.ramp.utils.IndustrialUnitSpecification;


import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RegistrationDashboardService {

    private final IndustrialUnitRegistrationRepository repository;

    public Map<String, Long> getOverallCounts() {
        Map<String, Long> map = new HashMap<>();
        map.put("total", repository.count());
        map.put("pending", repository.countByStatus(ApplicationStatus.SUBMITTED));
        map.put("approved", repository.countByStatus(ApplicationStatus.APPROVED));
        map.put("rejected", repository.countByStatus(ApplicationStatus.REJECTED));
        return map;
    }

    public Map<String, Long> getUserCounts(String userId) {
        Map<String, Long> map = new HashMap<>();
        map.put("total", repository.countByUserId(userId));
        map.put("pending", repository.countByUserIdAndStatus(userId, ApplicationStatus.SUBMITTED));
        map.put("approved", repository.countByUserIdAndStatus(userId, ApplicationStatus.APPROVED));
        map.put("rejected", repository.countByUserIdAndStatus(userId, ApplicationStatus.REJECTED));
        return map;
    }

    
    
    public Page<IndustrialUnitListDTO> getFilteredUnits(
            Long unit,
            String unitName,
            String unitLocation,
            ApplicationStatus status,
            LocalDateTime fromDate,
            LocalDateTime toDate,
            Pageable pageable) {

        Specification<IndustrialUnitRegistration> spec =
                IndustrialUnitSpecification.withFilters(
                        unit, unitName, unitLocation, status, fromDate, toDate
                );

        return repository.findAll(spec, pageable)
                .map(r -> new IndustrialUnitListDTO(
                        r.getId(),
                        r.getUnitDetails().getName(),
                        r.getUnitDetails().getLocation(),
                        r.getConstitutionType(),
                        r.getLegalDetails() != null ? r.getLegalDetails().getGstNo() : null,
                        r.getLegalDetails() != null ? r.getLegalDetails().getUdyamIemNo() : null,
                        r.getCreatedAt(),
                        r.getStatus()
                ));
    }
    
    
    public IndustrialUnitRegistration getSingleRegistration(
            Long id,
            String unitName,
            String unitLocation,
            String gstNo,
            String udyamIemNo,
            ApplicationStatus status) {

        return repository.findOne(
                IndustrialUnitSpecification.forSingleRecord(
                        id, unitName, unitLocation, gstNo, udyamIemNo, status
                )
        ).orElseThrow(() ->
                new RuntimeException("Industrial Unit not found")
        );
    }

    
    @Transactional
    public void updateStatus(Long id, ApplicationStatus status) {

        // Optional: validate state transition
        IndustrialUnitRegistration reg = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Industrial Unit not found"));

        // Example rule
        if (reg.getStatus() == ApplicationStatus.APPROVED) {
            throw new IllegalStateException("Approved applications cannot be modified");
        }

        int updated = repository.updateStatus(id, status);

        if (updated == 0) {
            throw new RuntimeException("Status update failed");
        }
    }


    
 }

  


