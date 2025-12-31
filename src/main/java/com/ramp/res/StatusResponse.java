package com.ramp.res;

import java.util.Optional;

import com.ramp.utils.StatusCode;
public class StatusResponse<T> {
    private int statusCode;
    private String status;
    private String message;
    private T data;

    public StatusResponse(StatusCode statusCode, T data, String message) {
        this.statusCode = statusCode.getCode();
        this.status = statusCode.name();
        this.message = message;
        this.data = data;
    }

    public static <T> StatusResponse<T> success(StatusCode statusCode, T data) {
        return new StatusResponse<T>(statusCode, data, statusCode.getMessage());
    }

    public static <T> StatusResponse<T> error(StatusCode statusCode, String message) {
        return new StatusResponse<T>(statusCode, null, message);
    }

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
    
    
}

	


