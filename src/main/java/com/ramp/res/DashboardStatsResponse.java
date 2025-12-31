package com.ramp.res;

public class DashboardStatsResponse {

    private long totalContent;
    private long pendingContent;
    private long approvedContent;
    private long rejectedContent;

    // Getters and Setters
    public long getTotalContent() {
        return totalContent;
    }

    public void setTotalContent(long totalContent) {
        this.totalContent = totalContent;
    }

    public long getPendingContent() {
        return pendingContent;
    }

    public void setPendingContent(long pendingContent) {
        this.pendingContent = pendingContent;
    }

    public long getApprovedContent() {
        return approvedContent;
    }

    public void setApprovedContent(long approvedContent) {
        this.approvedContent = approvedContent;
    }

    public long getRejectedContent() {
        return rejectedContent;
    }

    public void setRejectedContent(long rejectedContent) {
        this.rejectedContent = rejectedContent;
    }
}
