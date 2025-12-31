package com.ramp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ramp.entity.Content;
import com.ramp.entity.Users;
import com.ramp.enums.ContentStatus;
import com.ramp.repo.ContentRepository;
import com.ramp.repo.UserRepo;
import com.ramp.req.ContentCreateReq;
import com.ramp.res.ContentResponse;
import com.ramp.res.DashboardStatsResponse;
import com.ramp.res.StatusResponse;
import com.ramp.utils.StatusCode;

@Service
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;
    private final UserRepo userRepository;

    public ContentServiceImpl(ContentRepository contentRepository, UserRepo userRepository) {
        this.contentRepository = contentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public StatusResponse<ContentResponse> createContent(ContentCreateReq req, String username) {
        Users user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Content content = new Content();
        content.setTitle(req.getTitle());
        content.setDescription(req.getDescription());
        content.setPosterUrl(req.getPosterUrl());
        content.setPosterFilename(req.getPosterFilename());
        content.setStatus(ContentStatus.PENDING);
        content.setCreatedBy(user);
        content.setCreatedDate(LocalDateTime.now());

        Content savedContent = contentRepository.save(content);

        return new StatusResponse<>(StatusCode.CREATED, mapToResponse(savedContent),
                "Content created successfully and submitted for approval");
    }

    @Override
    public StatusResponse<List<ContentResponse>> getMyContent(String username) {
        Users user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Content> contentList = contentRepository.findByCreatedByOrderByCreatedDateDesc(user);
        List<ContentResponse> responseList = contentList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new StatusResponse<>(StatusCode.SUCCESS, responseList,
                "Content retrieved successfully");
    }

    @Override
    public StatusResponse<ContentResponse> updateContent(Long contentId, ContentCreateReq req, String username) {
        Users user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Content not found"));

        // Only allow updating own content and only if it's pending or rejected
        if (!content.getCreatedBy().getUserId().equals(user.getUserId())) {
            return new StatusResponse<>(StatusCode.FORBIDDEN, null,
                    "You can only update your own content");
        }

        if (content.getStatus() == ContentStatus.APPROVED) {
            return new StatusResponse<>(StatusCode.BAD_REQUEST, null,
                    "Cannot update approved content");
        }

        content.setTitle(req.getTitle());
        content.setDescription(req.getDescription());
        content.setPosterUrl(req.getPosterUrl());
        content.setPosterFilename(req.getPosterFilename());
        content.setStatus(ContentStatus.PENDING);
        content.setUpdatedDate(LocalDateTime.now());
        content.setRejectionReason(null);

        Content updatedContent = contentRepository.save(content);

        return new StatusResponse<>(StatusCode.SUCCESS, mapToResponse(updatedContent),
                "Content updated and resubmitted for approval");
    }

    @Override
    public StatusResponse<String> deleteContent(Long contentId, String username) {
        Users user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Content not found"));

        // Only allow deleting own content
        if (!content.getCreatedBy().getUserId().equals(user.getUserId())) {
            return new StatusResponse<>(StatusCode.FORBIDDEN, null,
                    "You can only delete your own content");
        }

        contentRepository.delete(content);

        return new StatusResponse<>(StatusCode.SUCCESS, "Content deleted successfully",
                "Content deleted successfully");
    }

    @Override
    public StatusResponse<List<ContentResponse>> getAllPendingContent() {
        List<Content> contentList = contentRepository.findByStatusOrderByCreatedDateDesc(ContentStatus.PENDING);
        List<ContentResponse> responseList = contentList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new StatusResponse<>(StatusCode.SUCCESS, responseList,
                "Pending content retrieved successfully");
    }

    @Override
    public StatusResponse<List<ContentResponse>> getAllContent() {
        List<Content> contentList = contentRepository.findAllByOrderByCreatedDateDesc();
        List<ContentResponse> responseList = contentList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new StatusResponse<>(StatusCode.SUCCESS, responseList,
                "All content retrieved successfully");
    }

    @Override
    public StatusResponse<ContentResponse> approveContent(Long contentId, String adminUsername) {
        Users admin = userRepository.findByUserName(adminUsername)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Content not found"));

        if (content.getStatus() != ContentStatus.PENDING) {
            return new StatusResponse<>(StatusCode.BAD_REQUEST, null,
                    "Only pending content can be approved");
        }

        content.setStatus(ContentStatus.APPROVED);
        content.setApprovedBy(admin);
        content.setApprovedDate(LocalDateTime.now());
        content.setUpdatedDate(LocalDateTime.now());
        content.setRejectionReason(null);

        Content approvedContent = contentRepository.save(content);

        return new StatusResponse<>(StatusCode.SUCCESS, mapToResponse(approvedContent),
                "Content approved successfully and is now live");
    }

    @Override
    public StatusResponse<ContentResponse> rejectContent(Long contentId, String rejectionReason, String adminUsername) {
        Users admin = userRepository.findByUserName(adminUsername)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Content not found"));

        if (content.getStatus() != ContentStatus.PENDING) {
            return new StatusResponse<>(StatusCode.BAD_REQUEST, null,
                    "Only pending content can be rejected");
        }

        content.setStatus(ContentStatus.REJECTED);
        content.setApprovedBy(admin);
        content.setRejectionReason(rejectionReason);
        content.setUpdatedDate(LocalDateTime.now());

        Content rejectedContent = contentRepository.save(content);

        return new StatusResponse<>(StatusCode.SUCCESS, mapToResponse(rejectedContent),
                "Content rejected");
    }

    @Override
    public StatusResponse<DashboardStatsResponse> getAdminDashboardStats() {
        DashboardStatsResponse stats = new DashboardStatsResponse();
        stats.setTotalContent(contentRepository.count());
        stats.setPendingContent(contentRepository.countByStatus(ContentStatus.PENDING));
        stats.setApprovedContent(contentRepository.countByStatus(ContentStatus.APPROVED));
        stats.setRejectedContent(contentRepository.countByStatus(ContentStatus.REJECTED));

        return new StatusResponse<>(StatusCode.SUCCESS, stats,
                "Dashboard stats retrieved successfully");
    }

    @Override
    public StatusResponse<List<ContentResponse>> getApprovedContent() {
        List<Content> contentList = contentRepository.findByStatusOrderByCreatedDateDesc(ContentStatus.APPROVED);
        List<ContentResponse> responseList = contentList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new StatusResponse<>(StatusCode.SUCCESS, responseList,
                "Approved content retrieved successfully");
    }

    @Override
    public StatusResponse<ContentResponse> getContentById(Long contentId) {
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("Content not found"));

        return new StatusResponse<>(StatusCode.SUCCESS, mapToResponse(content),
                "Content retrieved successfully");
    }

    private ContentResponse mapToResponse(Content content) {
        ContentResponse response = new ContentResponse();
        response.setId(content.getId());
        response.setTitle(content.getTitle());
        response.setDescription(content.getDescription());
        response.setPosterUrl(content.getPosterUrl());
        response.setPosterFilename(content.getPosterFilename());
        response.setStatus(content.getStatus());
        response.setRejectionReason(content.getRejectionReason());
        response.setCreatedDate(content.getCreatedDate());
        response.setUpdatedDate(content.getUpdatedDate());
        response.setApprovedDate(content.getApprovedDate());

        if (content.getCreatedBy() != null) {
            response.setCreatedByUsername(content.getCreatedBy().getUserName());
            response.setCreatedByName(content.getCreatedBy().getFullName());
        }

        if (content.getApprovedBy() != null) {
            response.setApprovedByUsername(content.getApprovedBy().getUserName());
            response.setApprovedByName(content.getApprovedBy().getFullName());
        }

        return response;
    }
}
