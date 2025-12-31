package com.ramp.service;

import java.util.List;

import com.ramp.req.ContentCreateReq;
import com.ramp.res.ContentResponse;
import com.ramp.res.DashboardStatsResponse;
import com.ramp.res.StatusResponse;

public interface ContentService {

    // Content Manager operations
    StatusResponse<ContentResponse> createContent(ContentCreateReq req, String username);

    StatusResponse<List<ContentResponse>> getMyContent(String username);

    StatusResponse<ContentResponse> updateContent(Long contentId, ContentCreateReq req, String username);

    StatusResponse<String> deleteContent(Long contentId, String username);

    // Admin operations
    StatusResponse<List<ContentResponse>> getAllPendingContent();

    StatusResponse<List<ContentResponse>> getAllContent();

    StatusResponse<ContentResponse> approveContent(Long contentId, String adminUsername);

    StatusResponse<ContentResponse> rejectContent(Long contentId, String rejectionReason, String adminUsername);

    StatusResponse<DashboardStatsResponse> getAdminDashboardStats();

    // Public operations (for landing page)
    StatusResponse<List<ContentResponse>> getApprovedContent();

    StatusResponse<ContentResponse> getContentById(Long contentId);
}
