package com.ramp.controller;

import com.ramp.dto.ApplicationDocumentDto;
import com.ramp.dto.IncentiveApplicationDto;
import com.ramp.service.IncentiveApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class IncentiveApplicationController {

    private final IncentiveApplicationService service;

    // ========== CREATE (DRAFT) ==========

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> create(
            @Valid @RequestBody IncentiveApplicationDto dto,
            Principal principal) {
        try {
            IncentiveApplicationDto created = service.create(dto, principal.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ========== UPDATE ==========

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> update(
            @PathVariable String id,
            @Valid @RequestBody IncentiveApplicationDto dto,
            Principal principal) {
        try {
            IncentiveApplicationDto updated = service.update(id, dto, principal.getName());
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ========== GET BY ID ==========

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> getById(
            @PathVariable String id,
            Principal principal) {
        try {
            IncentiveApplicationDto response = service.getById(id, principal.getName());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ========== UPLOAD DOCUMENT ==========

    @PostMapping("/{id}/upload")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> uploadDocument(
            @PathVariable String id,
            @RequestParam("documentType") String documentType,
            @RequestParam("file") MultipartFile file,
            Principal principal) {
        try {
            ApplicationDocumentDto doc = service.uploadDocument(id, documentType, file, principal.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(doc);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ========== SUBMIT ==========

    @PostMapping("/{id}/submit")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> submit(
            @PathVariable String id,
            Principal principal) {
        try {
            IncentiveApplicationDto submitted = service.submit(id, principal.getName());
            return ResponseEntity.ok(submitted);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ========== LIST ALL (PAGINATED) ==========

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Page<IncentiveApplicationDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IncentiveApplicationDto> result = service.getAll(pageable);
        return ResponseEntity.ok(result);
    }

    // ========== MY APPLICATIONS ==========

    @GetMapping("/my")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<Page<IncentiveApplicationDto>> getMyApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IncentiveApplicationDto> result = service.getByUser(principal.getName(), pageable);
        return ResponseEntity.ok(result);
    }
}
