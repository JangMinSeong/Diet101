package com.d101.back.exception.response;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ExceptionStatus implements ExceptionInfo {

    // NOT FOUND
    USER_NOT_FOUND(1000, HttpStatus.NOT_FOUND.value(), "사용자가 존재하지 않습니다."),

    // BAD_REQUEST
    DUPLICATE_USER(2000, HttpStatus.BAD_REQUEST.value(), "사용자가 이미 존재합니다."),
    JWT_TOKEN_ALIVE(2001, HttpStatus.BAD_REQUEST.value(), "JWT 토큰이 만료되지 않아 재발급이 불가능합니다."),
    REFRESH_TOKEN_INVALID(2002, HttpStatus.BAD_REQUEST.value(), "Refresh 토큰이 유효하지 않습니다."),

    // UNAUTHORIZED
    UNAUTHORIZED(3000, HttpStatus.UNAUTHORIZED.value(), "사용자가 인증되지 않았습니다."),
    JWT_TOKEN_INVALID(3001, HttpStatus.UNAUTHORIZED.value(), "JWT 토큰 인증에 실패했습니다."),
    OAUTH_LOGIN_FAIL(3002, HttpStatus.UNAUTHORIZED.value(), "소셜 로그인 중 문제가 발생했습니다."),
    JWT_TOKEN_EXPIRED(3003, HttpStatus.UNAUTHORIZED.value(), "JWT 토큰이 만료되었습니다."),
    UNSUPPORTED_JWT_TOKEN(3004, HttpStatus.UNAUTHORIZED.value(), "지원되지 않는 JWT 토큰 형식입니다."),
    JWT_CLAIMS_STRING_IS_EMPTY(3005, HttpStatus.UNAUTHORIZED.value(), "JWT claims string is empty"),

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
