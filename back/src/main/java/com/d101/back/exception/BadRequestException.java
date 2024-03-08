package com.d101.back.exception;

import com.d101.back.exception.response.ExceptionInfo;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final ExceptionInfo exceptionInfo;

    public BadRequestException(ExceptionInfo exceptionInfo) {
        super(exceptionInfo.getMessage());
        this.exceptionInfo = exceptionInfo;
    }
}
