package com.ramp.utils;

public enum StatusCode {
    SUCCESS(200, "Request processed successfully"),
    CREATED(201, "Resource created successfully"),
    BAD_REQUEST(400, "Invalid request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Resource not found"),
    CONFLICT(409, "Conflict - resource already exists"),
    INTERNAL_ERROR(500, "Internal server error");

    private final int code;
    private final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
