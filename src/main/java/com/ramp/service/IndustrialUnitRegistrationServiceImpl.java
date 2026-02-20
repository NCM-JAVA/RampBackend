package com.ramp.service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ramp.entity.IndustrialUnitRegistration;
import com.ramp.enums.ApplicationStatus;
import com.ramp.mapper.IndustrialUnitRegistrationMapper;
import com.ramp.repo.IndustrialUnitRegistrationRepository;
import com.ramp.req.*;
import com.ramp.res.IndustrialUnitRegistrationResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class IndustrialUnitRegistrationServiceImpl implements IndustrialUnitRegistrationService {

    private final IndustrialUnitRegistrationRepository repository;
    private final IndustrialUnitRegistrationMapper mapper;

    // ========== STEP-BY-STEP REGISTRATION ==========

    @Override
    public List<IndustrialUnitRegistrationResponse> createDraft(String userId) {
        List<IndustrialUnitRegistration> existingDrafts =
                repository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, ApplicationStatus.DRAFT);

        if (!existingDrafts.isEmpty()) {
            return existingDrafts.stream()
                    .map(IndustrialUnitRegistrationResponse::fromEntityWithDetails)
                    .collect(Collectors.toList());
        }

        int currentYear = Year.now().getValue();
        String customId = "APIDIP-" + userId + "-" + currentYear;

        IndustrialUnitRegistration entity = new IndustrialUnitRegistration();
        entity.setId(customId);
        entity.setUserId(userId);
        entity.setStatus(ApplicationStatus.DRAFT);
        entity.setCurrentStep(0);

        IndustrialUnitRegistration saved = repository.save(entity);
        return List.of(IndustrialUnitRegistrationResponse.fromEntityWithDetails(saved));
    }

    @Override
    public IndustrialUnitRegistrationResponse saveUnitDetails(String id, UnitDetailsReq request, String userId) {
        IndustrialUnitRegistration entity = getAndValidateDraft(id, userId);
        mapper.mapUnitDetailsToEntity(request, entity);
        entity.setCurrentStep(Math.max(entity.getCurrentStep(), 1));

        IndustrialUnitRegistration saved = repository.save(entity);
        return IndustrialUnitRegistrationResponse.fromEntityWithDetails(saved);
    }

    @Override
    public IndustrialUnitRegistrationResponse saveConstitution(String id, ConstitutionReq request, String userId) {
        IndustrialUnitRegistration entity = getAndValidateDraft(id, userId);
        mapper.mapConstitutionToEntity(request, entity);
        entity.setCurrentStep(Math.max(entity.getCurrentStep(), 2));

        IndustrialUnitRegistration saved = repository.save(entity);
        return IndustrialUnitRegistrationResponse.fromEntityWithDetails(saved);
    }

    @Override
    public IndustrialUnitRegistrationResponse saveOperationalPlan(String id, OperationalPlanReq request, String userId) {
        IndustrialUnitRegistration entity = getAndValidateDraft(id, userId);
        mapper.mapOperationalPlanToEntity(request, entity);
        entity.setCurrentStep(Math.max(entity.getCurrentStep(), 3));

        IndustrialUnitRegistration saved = repository.save(entity);
        return IndustrialUnitRegistrationResponse.fromEntityWithDetails(saved);
    }

    @Override
    public IndustrialUnitRegistrationResponse saveLegalDetails(String id, LegalDetailsReq request, String userId) {
        IndustrialUnitRegistration entity = getAndValidateDraft(id, userId);
        mapper.mapLegalDetailsToEntity(request, entity);
        entity.setCurrentStep(Math.max(entity.getCurrentStep(), 4));

        IndustrialUnitRegistration saved = repository.save(entity);
        return IndustrialUnitRegistrationResponse.fromEntityWithDetails(saved);
    }

    @Override
    public IndustrialUnitRegistrationResponse saveFinancials(String id, FixedCapitalInvestmentReq request, String userId) {
        IndustrialUnitRegistration entity = getAndValidateDraft(id, userId);
        mapper.mapFinancialsToEntity(request, entity);
        entity.setCurrentStep(Math.max(entity.getCurrentStep(), 5));

        IndustrialUnitRegistration saved = repository.save(entity);
        return IndustrialUnitRegistrationResponse.fromEntityWithDetails(saved);
    }

    @Override
    public IndustrialUnitRegistrationResponse saveEmployment(String id, EmploymentReq request, String userId) {
        IndustrialUnitRegistration entity = getAndValidateDraft(id, userId);
        mapper.mapEmploymentToEntity(request, entity);
        entity.setCurrentStep(Math.max(entity.getCurrentStep(), 6));

        IndustrialUnitRegistration saved = repository.save(entity);
        return IndustrialUnitRegistrationResponse.fromEntityWithDetails(saved);
    }

    @Override
    public IndustrialUnitRegistrationResponse saveDeclaration(String id, DeclarationReq request, String userId) {
        IndustrialUnitRegistration entity = getAndValidateDraft(id, userId);
        mapper.mapDeclarationToEntity(request, entity);
        entity.setCurrentStep(Math.max(entity.getCurrentStep(), 7));

        IndustrialUnitRegistration saved = repository.save(entity);
        return IndustrialUnitRegistrationResponse.fromEntityWithDetails(saved);
    }

    @Override
    public IndustrialUnitRegistrationResponse submitRegistration(String id, String userId) {
        IndustrialUnitRegistration entity = getAndValidateDraft(id, userId);

        // Validate required fields
        if (entity.getUnitDetails() == null) {
            throw new IllegalArgumentException("Unit details are required");
        }
        if (entity.getConstitutionType() == null) {
            throw new IllegalArgumentException("Constitution details are required");
        }
        if (entity.getIndustryType() == null) {
            throw new IllegalArgumentException("Operational plan is required");
        }
        if (entity.getDeclaration() == null || !Boolean.TRUE.equals(entity.getDeclaration().getIsDeclared())) {
            throw new IllegalArgumentException("Declaration must be accepted to submit registration");
        }

        entity.setStatus(ApplicationStatus.SUBMITTED);
        entity.setSubmittedAt(LocalDateTime.now());

        IndustrialUnitRegistration saved = repository.save(entity);
        return IndustrialUnitRegistrationResponse.fromEntityWithDetails(saved);
    }

    private IndustrialUnitRegistration getAndValidateDraft(String id, String userId) {
        IndustrialUnitRegistration entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found with id: " + id));

        if (!entity.getUserId().equals(userId)) {
            throw new IllegalArgumentException("You don't have permission to update this registration");
        }

        if (entity.getStatus() != ApplicationStatus.DRAFT) {
            throw new IllegalArgumentException("Only draft registrations can be updated");
        }

        return entity;
    }

    // ========== LEGACY: SUBMIT ALL AT ONCE ==========

    @Override
    public IndustrialUnitRegistrationResponse submitRegistration(IndustrialUnitRegistrationReq request, String userId) {
        // Check if user already has a registration
        boolean exists = repository.existsByUserId(userId);
        if (exists) {
            throw new IllegalArgumentException("You have already applied for unit registration. Only one registration per user is allowed.");
        }

        // Validate declaration
        if (request.getDeclaration() == null || !Boolean.TRUE.equals(request.getDeclaration().getIsDeclared())) {
            throw new IllegalArgumentException("Declaration must be accepted to submit registration");
        }

        // Map DTO to entity
        IndustrialUnitRegistration entity = mapper.toEntity(request, userId);

        // Set custom ID
        int currentYear = Year.now().getValue();
        entity.setId("APIDIP-" + userId + "-" + currentYear);

        // Set status to SUBMITTED
        entity.setStatus(ApplicationStatus.SUBMITTED);
        entity.setSubmittedAt(LocalDateTime.now());

        // Save and return response
        IndustrialUnitRegistration saved = repository.save(entity);
        return IndustrialUnitRegistrationResponse.fromEntityWithDetails(saved);
    }

    @Override
    public List<IndustrialUnitRegistrationResponse> getRegistrationsByUser(String userId) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(IndustrialUnitRegistrationResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public IndustrialUnitRegistrationResponse getRegistrationByUserId(String userId) {
        IndustrialUnitRegistration registration = repository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No registration found for user: " + userId));
        return IndustrialUnitRegistrationResponse.fromEntityWithDetails(registration);
    }

    @Override
    public IndustrialUnitRegistrationResponse getRegistrationById(String id, String userId) {
        IndustrialUnitRegistration registration = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found with id: " + id));

        // Check if user owns this registration (unless admin check is done in controller)
        if (userId != null && !registration.getUserId().equals(userId)) {
            throw new IllegalArgumentException("You don't have permission to view this registration");
        }

        return IndustrialUnitRegistrationResponse.fromEntityWithDetails(registration);
    }

    @Override
    public List<IndustrialUnitRegistrationResponse> getAllRegistrations() {
        return repository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(IndustrialUnitRegistrationResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<IndustrialUnitRegistrationResponse> getRegistrationsByStatus(ApplicationStatus status) {
        return repository.findByStatusOrderByCreatedAtDesc(status)
                .stream()
                .map(IndustrialUnitRegistrationResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public IndustrialUnitRegistrationResponse approveRegistration(String id, String reviewerId) {
        IndustrialUnitRegistration registration = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found with id: " + id));

        registration.setStatus(ApplicationStatus.APPROVED);
        registration.setReviewedBy(reviewerId);
        registration.setReviewedAt(LocalDateTime.now());
        registration.setRejectionReason(null);

        IndustrialUnitRegistration saved = repository.save(registration);
        return IndustrialUnitRegistrationResponse.fromEntityWithDetails(saved);
    }

    @Override
    public IndustrialUnitRegistrationResponse rejectRegistration(String id, String reason, String reviewerId) {
        IndustrialUnitRegistration registration = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found with id: " + id));

        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("Rejection reason is required");
        }

        registration.setStatus(ApplicationStatus.REJECTED);
        registration.setRejectionReason(reason);
        registration.setReviewedBy(reviewerId);
        registration.setReviewedAt(LocalDateTime.now());

        IndustrialUnitRegistration saved = repository.save(registration);
        return IndustrialUnitRegistrationResponse.fromEntityWithDetails(saved);
    }

    @Override
    public IndustrialUnitRegistrationResponse markUnderReview(String id, String reviewerId) {
        IndustrialUnitRegistration registration = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found with id: " + id));

        registration.setStatus(ApplicationStatus.UNDER_REVIEW);
        registration.setReviewedBy(reviewerId);
        registration.setReviewedAt(LocalDateTime.now());

        IndustrialUnitRegistration saved = repository.save(registration);
        return IndustrialUnitRegistrationResponse.fromEntityWithDetails(saved);
    }

    @Override
    public IndustrialUnitRegistration create(IndustrialUnitRegistration registration) {
        return repository.save(registration);
    }
}
