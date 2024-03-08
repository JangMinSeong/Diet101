package com.d101.back.exception.handler;

import com.d101.back.exception.BadRequestException;
import com.d101.back.exception.NoSuchDataException;
import com.d101.back.exception.UnAuthorizedException;
import com.d101.back.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorResponse badRequestExceptionHandler(BadRequestException e) {
        return new ErrorResponse(e.getExceptionInfo());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchFieldException.class)
    public ErrorResponse notFoundExceptionHandler(NoSuchDataException e) {
        return new ErrorResponse(e.getExceptionInfo());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException.class)
    public ErrorResponse unAuthorizedExceptionHandler(UnAuthorizedException e) {
        return new ErrorResponse(e.getExceptionInfo());
    }

}