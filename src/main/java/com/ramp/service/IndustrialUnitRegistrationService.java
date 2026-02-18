package com.ramp.service;

import java.util.List;

import com.ramp.entity.IndustrialUnitRegistration;
import com.ramp.enums.ApplicationStatus;
import com.ramp.req.*;
import com.ramp.res.IndustrialUnitRegistrationResponse;

public interface IndustrialUnitRegistrationService {

    // Step-by-step registration
    IndustrialUnitRegistrationResponse createDraft(String userId);

    IndustrialUnitRegistrationResponse saveUnitDetails(String id, UnitDetailsReq request, String userId);

    IndustrialUnitRegistrationResponse saveConstitution(String id, ConstitutionReq request, String userId);

    IndustrialUnitRegistrationResponse saveOperationalPlan(String id, OperationalPlanReq request, String userId);

    IndustrialUnitRegistrationResponse saveLegalDetails(String id, LegalDetailsReq request, String userId);

    IndustrialUnitRegistrationResponse saveFinancials(String id, FixedCapitalInvestmentReq request, String userId);

    IndustrialUnitRegistrationResponse saveEmployment(String id, EmploymentReq request, String userId);

    IndustrialUnitRegistrationResponse saveDeclaration(String id, DeclarationReq request, String userId);

    IndustrialUnitRegistrationResponse submitRegistration(String id, String userId);

    // Legacy: User operations (submit all at once)
    IndustrialUnitRegistrationResponse submitRegistration(IndustrialUnitRegistrationReq request, String userId);

    List<IndustrialUnitRegistrationResponse> getRegistrationsByUser(String userId);

    IndustrialUnitRegistrationResponse getRegistrationByUserId(String userId);

    IndustrialUnitRegistrationResponse getRegistrationById(String id, String userId);

    // Admin operations
    List<IndustrialUnitRegistrationResponse> getAllRegistrations();

    List<IndustrialUnitRegistrationResponse> getRegistrationsByStatus(ApplicationStatus status);

    IndustrialUnitRegistrationResponse approveRegistration(String id, String reviewerId);

    IndustrialUnitRegistrationResponse rejectRegistration(String id, String reason, String reviewerId);

    IndustrialUnitRegistrationResponse markUnderReview(String id, String reviewerId);

    // Legacy method (kept for backward compatibility)
    IndustrialUnitRegistration create(IndustrialUnitRegistration registration);
}
