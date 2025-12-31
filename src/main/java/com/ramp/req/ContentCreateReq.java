package com.ramp.req;

import javax.validation.constraints.NotBlank;

public class ContentCreateReq {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private String posterUrl;

    private String posterFilename;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getPosterFilename() {
        return posterFilename;
    }

    public void setPosterFilename(String posterFilename) {
        this.posterFilename = posterFilename;
    }
}
