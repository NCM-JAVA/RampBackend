package com.ramp.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ramp.entity.IndustrialUnitRegistration;
import com.ramp.req.ContentApprovalReq;
import com.ramp.res.ContentResponse;
import com.ramp.res.DashboardStatsResponse;
import com.ramp.res.StatusResponse;
import com.ramp.service.ContentService;
import com.ramp.service.IndustrialUnitRegistrationService;
import com.ramp.service.RegistrationDashboardService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
public class AdminController {

    private final ContentService contentService;
    private final IndustrialUnitRegistrationService industrialUnitRegistrationService;

    // ✅ SINGLE constructor with ALL dependencies
    public AdminController(
            ContentService contentService,
            IndustrialUnitRegistrationService industrialUnitRegistrationService) {

        this.contentService = contentService;
        this.industrialUnitRegistrationService = industrialUnitRegistrationService;
    }

    @GetMapping("/dashboard/stats")
    public ResponseEntity<StatusResponse<DashboardStatsResponse>> getDashboardStats() {
        StatusResponse<DashboardStatsResponse> response = contentService.getAdminDashboardStats();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/content/pending")
    public ResponseEntity<StatusResponse<List<ContentResponse>>> getPendingContent() {
        StatusResponse<List<ContentResponse>> response = contentService.getAllPendingContent();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/content/all")
    public ResponseEntity<StatusResponse<List<ContentResponse>>> getAllContent() {
        StatusResponse<List<ContentResponse>> response = contentService.getAllContent();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/content/approve/{contentId}")
    public ResponseEntity<StatusResponse<ContentResponse>> approveContent(
            @PathVariable Long contentId, Principal principal) {
        StatusResponse<ContentResponse> response =
                contentService.approveContent(contentId, principal.getName());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/content/reject")
    public ResponseEntity<StatusResponse<ContentResponse>> rejectContent(
            @Valid @RequestBody ContentApprovalReq req, Principal principal) {
        StatusResponse<ContentResponse> response =
                contentService.rejectContent(
                        req.getContentId(),
                        req.getRejectionReason(),
                        principal.getName());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/industrial-unit/register")
    public ResponseEntity<IndustrialUnitRegistration> registerIndustrialUnit(
            @RequestBody IndustrialUnitRegistration registration) {

        System.out.println(">>> CONTROLLER HIT <<<");

        IndustrialUnitRegistration saved =
                industrialUnitRegistrationService.create(registration);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }
   

}
