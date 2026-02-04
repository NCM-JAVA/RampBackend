package com.ramp.service;

import java.util.List;

import com.ramp.entity.IndustrialUnitRegistration;
import com.ramp.enums.ApplicationStatus;
import com.ramp.req.*;
import com.ramp.res.IndustrialUnitRegistrationResponse;

public interface IndustrialUnitRegistrationService {

    // Step-by-step registration
    IndustrialUnitRegistrationResponse createDraft(String userId);

    IndustrialUnitRegistrationResponse saveUnitDetails(Long id, UnitDetailsReq request, String userId);

    IndustrialUnitRegistrationResponse saveConstitution(Long id, ConstitutionReq request, String userId);

    IndustrialUnitRegistrationResponse saveOperationalPlan(Long id, OperationalPlanReq request, String userId);

    IndustrialUnitRegistrationResponse saveLegalDetails(Long id, LegalDetailsReq request, String userId);

    IndustrialUnitRegistrationResponse saveFinancials(Long id, FixedCapitalInvestmentReq request, String userId);

    IndustrialUnitRegistrationResponse saveEmployment(Long id, EmploymentReq request, String userId);

    IndustrialUnitRegistrationResponse saveDeclaration(Long id, DeclarationReq request, String userId);

    IndustrialUnitRegistrationResponse submitRegistration(Long id, String userId);

    // Legacy: User operations (submit all at once)
    IndustrialUnitRegistrationResponse submitRegistration(IndustrialUnitRegistrationReq request, String userId);

    List<IndustrialUnitRegistrationResponse> getRegistrationsByUser(String userId);

    IndustrialUnitRegistrationResponse getRegistrationById(Long id, String userId);

    // Admin operations
    List<IndustrialUnitRegistrationResponse> getAllRegistrations();

    List<IndustrialUnitRegistrationResponse> getRegistrationsByStatus(ApplicationStatus status);

    IndustrialUnitRegistrationResponse approveRegistration(Long id, String reviewerId);

    IndustrialUnitRegistrationResponse rejectRegistration(Long id, String reason, String reviewerId);

    IndustrialUnitRegistrationResponse markUnderReview(Long id, String reviewerId);

    // Legacy method (kept for backward compatibility)
    IndustrialUnitRegistration create(IndustrialUnitRegistration registration);
}
