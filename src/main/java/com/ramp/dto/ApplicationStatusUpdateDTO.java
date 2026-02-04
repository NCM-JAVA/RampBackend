package com.ramp.dto;

import javax.validation.constraints.NotNull;

import com.ramp.enums.ApplicationStatus;

import lombok.Data;

@Data
public class ApplicationStatusUpdateDTO {

    @NotNull
    private ApplicationStatus status;

    private String remarks; // optional (can be stored later)
}
