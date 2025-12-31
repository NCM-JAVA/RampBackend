package com.ramp.req;

import javax.validation.constraints.NotNull;

public class ContentApprovalReq {

    @NotNull(message = "Content ID is required")
    private Long contentId;

    private String rejectionReason;

    // Getters and Setters
    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
