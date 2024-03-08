package com.d101.back.exception.response;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonPropertyOrder({"code", "status", "message", "timestamp"})
public class ErrorResponse implements ExceptionInfo {

    private final int code;
    private final int status;
    private final String message;
    private final LocalDateTime timestamp;

    public ErrorResponse(ExceptionInfo status) {
        this.code = status.getCode();
        this.status = status.getStatus();
        this.message = status.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(ExceptionInfo status, String message) {
        this.code = status.getCode();
        this.status = status.getStatus();
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

}

