package com.ramp.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ramp.req.ContentCreateReq;
import com.ramp.res.ContentResponse;
import com.ramp.res.StatusResponse;
import com.ramp.service.ContentService;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    private final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CONTENT_MANAGER')")
    public ResponseEntity<StatusResponse<ContentResponse>> createContent(
            @Valid @RequestBody ContentCreateReq req, Principal principal) {
        StatusResponse<ContentResponse> response = contentService.createContent(req, principal.getName());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/my-content")
    @PreAuthorize("hasAuthority('CONTENT_MANAGER')")
    public ResponseEntity<StatusResponse<List<ContentResponse>>> getMyContent(Principal principal) {
        StatusResponse<List<ContentResponse>> response = contentService.getMyContent(principal.getName());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{contentId}")
    @PreAuthorize("hasAuthority('CONTENT_MANAGER')")
    public ResponseEntity<StatusResponse<ContentResponse>> updateContent(
            @PathVariable Long contentId,
            @Valid @RequestBody ContentCreateReq req,
            Principal principal) {
        StatusResponse<ContentResponse> response = contentService.updateContent(contentId, req, principal.getName());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{contentId}")
    @PreAuthorize("hasAuthority('CONTENT_MANAGER')")
    public ResponseEntity<StatusResponse<String>> deleteContent(
            @PathVariable Long contentId, Principal principal) {
        StatusResponse<String> response = contentService.deleteContent(contentId, principal.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<StatusResponse<ContentResponse>> getContentById(@PathVariable Long contentId) {
        StatusResponse<ContentResponse> response = contentService.getContentById(contentId);
        return ResponseEntity.ok(response);
    }

    // Public endpoint for landing page
    @GetMapping("/public/approved")
    public ResponseEntity<StatusResponse<List<ContentResponse>>> getApprovedContent() {
        StatusResponse<List<ContentResponse>> response = contentService.getApprovedContent();
        return ResponseEntity.ok(response);
    }
}
