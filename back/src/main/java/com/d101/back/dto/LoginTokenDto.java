package com.d101.back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginTokenDto {
    private String email;
    private String accessToken;
    private String refreshToken;
}
