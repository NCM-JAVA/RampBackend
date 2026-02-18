package com.ramp.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ramp.enums.ApplicationStatus;
import com.ramp.req.*;
import com.ramp.res.IndustrialUnitRegistrationResponse;
import com.ramp.service.FileStorageService;
import com.ramp.service.IndustrialUnitRegistrationService;
import com.ramp.utils.FileStorageException;

import lombok.RequiredArgsConstructor;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/industrial-unit")
@RequiredArgsConstructor
public class IndustrialUnitRegistrationController {

    private final IndustrialUnitRegistrationService service;
    private final FileStorageService fileStorageService;

    // ========== FILE UPLOAD ENDPOINTS ==========

    @PostMapping("/files/upload")
    @PreAuthorize("hasAnyAuthority('Entrepreneur', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            Principal principal) {
        try {
            String filePath = fileStorageService.storeFile(file, principal.getName());

            FileUploadResponse response = new FileUploadResponse(
                    file.getOriginalFilename(),
                    filePath,
                    file.getContentType(),
                    file.getSize()
            );

            return ResponseEntity.ok(response);
        } catch (FileStorageException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/files/{userId}/{fileName:.+}")
    @PreAuthorize("hasAnyAuthority('Entrepreneur', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> downloadFile(
            @PathVariable String userId,
            @PathVariable String fileName,
            Principal principal,
            HttpServletRequest request) {
        try {
            // Check if user is accessing their own files or is admin
            if (!principal.getName().equals(userId) && !isAdmin(principal)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You don't have permission to access this file");
            }

            Resource resource = fileStorageService.loadFileAsResource(userId, fileName);

            String contentType = "application/octet-stream";
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (Exception e) {
                // Use default content type
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (FileStorageException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ========== STEP-BY-STEP REGISTRATION ENDPOINTS ==========

    @PostMapping("/register/draft")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> createDraft(Principal principal) {
        try {
            IndustrialUnitRegistrationResponse response = service.createDraft(principal.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/register/{id}/step/unit-details")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> saveUnitDetails(
            @PathVariable String id,
            @Valid @RequestBody UnitDetailsReq request,
            Principal principal) {
        try {
            IndustrialUnitRegistrationResponse response =
                    service.saveUnitDetails(id, request, principal.getName());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/register/{id}/step/constitution")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> saveConstitution(
            @PathVariable String id,
            @Valid @RequestBody ConstitutionReq request,
            Principal principal) {
        try {
            IndustrialUnitRegistrationResponse response =
                    service.saveConstitution(id, request, principal.getName());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/register/{id}/step/operational-plan")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> saveOperationalPlan(
            @PathVariable String id,
            @Valid @RequestBody OperationalPlanReq request,
            Principal principal) {
        try {
            IndustrialUnitRegistrationResponse response =
                    service.saveOperationalPlan(id, request, principal.getName());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/register/{id}/step/legal-details")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> saveLegalDetails(
            @PathVariable String id,
            @Valid @RequestBody LegalDetailsReq request,
            Principal principal) {
        try {
            IndustrialUnitRegistrationResponse response =
                    service.saveLegalDetails(id, request, principal.getName());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/register/{id}/step/financials")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> saveFinancials(
            @PathVariable String id,
            @Valid @RequestBody FixedCapitalInvestmentReq request,
            Principal principal) {
        try {
            IndustrialUnitRegistrationResponse response =
                    service.saveFinancials(id, request, principal.getName());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/register/{id}/step/employment")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> saveEmployment(
            @PathVariable String id,
            @Valid @RequestBody EmploymentReq request,
            Principal principal) {
        try {
            IndustrialUnitRegistrationResponse response =
                    service.saveEmployment(id, request, principal.getName());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/register/{id}/step/declaration")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> saveDeclaration(
            @PathVariable String id,
            @Valid @RequestBody DeclarationReq request,
            Principal principal) {
        try {
            IndustrialUnitRegistrationResponse response =
                    service.saveDeclaration(id, request, principal.getName());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register/{id}/submit")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> submitRegistration(
            @PathVariable String id,
            Principal principal) {
        try {
            IndustrialUnitRegistrationResponse response =
                    service.submitRegistration(id, principal.getName());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ========== LEGACY: SUBMIT ALL AT ONCE ==========

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> register(
            @Valid @RequestBody IndustrialUnitRegistrationReq request,
            Principal principal) {
        try {
            IndustrialUnitRegistrationResponse response =
                    service.submitRegistration(request, principal.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/my-registration")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getMyRegistration(Principal principal) {
        try {
            IndustrialUnitRegistrationResponse response =
                    service.getRegistrationByUserId(principal.getName());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/my-registrations")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<List<IndustrialUnitRegistrationResponse>> getMyRegistrations(Principal principal) {
        List<IndustrialUnitRegistrationResponse> registrations =
                service.getRegistrationsByUser(principal.getName());
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getRegistrationById(
            @PathVariable String id,
            Principal principal) {
        try {
            // If user is admin, pass null to skip ownership check
            String userId = isAdmin(principal) ? null : principal.getName();
            IndustrialUnitRegistrationResponse response = service.getRegistrationById(id, userId);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ========== ADMIN ENDPOINTS ==========

    @GetMapping("/admin/all")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<IndustrialUnitRegistrationResponse>> getAllRegistrations() {
        List<IndustrialUnitRegistrationResponse> registrations = service.getAllRegistrations();
        return ResponseEntity.ok(registrations);
    }

    @GetMapping("/admin/status/{status}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getRegistrationsByStatus(@PathVariable String status) {
        try {
            ApplicationStatus appStatus = ApplicationStatus.valueOf(status.toUpperCase());
            List<IndustrialUnitRegistrationResponse> registrations =
                    service.getRegistrationsByStatus(appStatus);
            return ResponseEntity.ok(registrations);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body("Invalid status. Valid values: DRAFT, SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED");
        }
    }

    @PutMapping("/admin/{id}/approve")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> approveRegistration(
            @PathVariable String id,
            Principal principal) {
        try {
            IndustrialUnitRegistrationResponse response =
                    service.approveRegistration(id, principal.getName());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/admin/{id}/reject")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> rejectRegistration(
            @PathVariable String id,
            @RequestParam("reason") String reason,
            Principal principal) {
        try {
            IndustrialUnitRegistrationResponse response =
                    service.rejectRegistration(id, reason, principal.getName());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/admin/{id}/under-review")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> markUnderReview(
            @PathVariable String id,
            Principal principal) {
        try {
            IndustrialUnitRegistrationResponse response =
                    service.markUnderReview(id, principal.getName());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ========== HELPER METHODS ==========

    private boolean isAdmin(Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN")
                        || auth.getAuthority().equals("SUPER_ADMIN"));
    }
}
