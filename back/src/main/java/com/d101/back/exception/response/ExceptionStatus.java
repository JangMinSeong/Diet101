package com.d101.back.exception.response;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ExceptionStatus implements ExceptionInfo {

    // NOT FOUND
    USER_NOT_FOUND(1000, HttpStatus.NOT_FOUND.value(), "사용자가 존재하지 않습니다."),

    // BAD_REQUEST
    DUPLICATE_USER(2000, HttpStatus.BAD_REQUEST.value(), "사용자가 이미 존재합니다."),

    // UNAUTHORIZED
    UNAUTHORIZED(3000, HttpStatus.UNAUTHORIZED.value(), "사용자가 인증되지 않았습니다."),
    JWT_TOKEN_INVALID(3001, HttpStatus.UNAUTHORIZED.value(), "JWT 토큰 인증에 실패했습니다."),
    OAUTH_LOGIN_FAIL(3002, HttpStatus.UNAUTHORIZED.value(), "소셜 로그인 중 문제가 발생했습니다."),
    ;

    private final int code;
    private final int status;
    private final String message;

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
