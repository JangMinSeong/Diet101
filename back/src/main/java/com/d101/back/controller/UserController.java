package com.d101.back.controller;

import com.d101.back.dto.LoginTokenDto;
import com.d101.back.dto.oauth.KakaoLoginReq;
import com.d101.back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login/kakao")
    public ResponseEntity<LoginTokenDto> loginOauth(@RequestBody KakaoLoginReq kakaoLoginReq) {
        return ResponseEntity.ok(userService.loginOauth(kakaoLoginReq));
    }
}
