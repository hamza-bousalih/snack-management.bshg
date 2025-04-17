package org.bshg.demo.zutils.exception;

import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

public class ApiError {
    private String message;
    private HttpStatus status;
    private int statusCode;
    private final Timestamp timestamp;

    public ApiError() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public ApiError(ApiException e) {
        this();
        this.message = e.getMessage();
        this.status = e.getStatus();
        this.statusCode = status.value();
    }

    public static ApiError status(HttpStatus status) {
        var apiError = new ApiError();
        apiError.setStatus(status);
        return apiError;
    }

    public ApiError message(String s) {
        this.message = s;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
        this.statusCode = this.status.value();
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
