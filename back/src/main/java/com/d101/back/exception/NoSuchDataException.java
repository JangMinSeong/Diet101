package com.d101.back.exception;

import com.d101.back.exception.response.ExceptionInfo;
import lombok.Getter;

@Getter
public class NoSuchDataException extends RuntimeException{

    private final ExceptionInfo exceptionInfo;

    public NoSuchDataException(ExceptionInfo exceptionInfo) {
        super(exceptionInfo.getMessage());
        this.exceptionInfo = exceptionInfo;
    }

}