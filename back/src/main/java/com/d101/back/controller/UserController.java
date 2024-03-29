package com.d101.back.controller;

import com.d101.back.dto.LoginTokenDto;
import com.d101.back.dto.UserDto;
import com.d101.back.dto.oauth.KakaoLoginReq;
import com.d101.back.dto.request.LoginReq;
import com.d101.back.dto.request.ModifyUserReq;
import com.d101.back.dto.request.UpdateAllergyReq;
import com.d101.back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login/kakao")
    public ResponseEntity<LoginTokenDto> loginOauth(@RequestBody KakaoLoginReq kakaoLoginReq) {
        return ResponseEntity.ok(userService.loginOauth(kakaoLoginReq));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginTokenDto> login(@RequestBody LoginReq loginReq) {
        return ResponseEntity.ok(userService.login(loginReq));
    }

    @PostMapping("/reissue")
    public ResponseEntity<LoginTokenDto> reissue(@RequestBody LoginTokenDto loginTokenDto) {
        return ResponseEntity.ok(userService.reissue(loginTokenDto));
    }

    @PostMapping("/info/profile")
    public ResponseEntity<?> updateUserInfo(@RequestBody ModifyUserReq req, Authentication authentication) {
        userService.updateUserInfo(authentication.getName(),req);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/info/allergy")
    public ResponseEntity<?> updateAllergy(@RequestBody UpdateAllergyReq req, Authentication authentication) {
        userService.updateAllergy(authentication.getName(), req.getAllergies());
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/info/allergy")
    public ResponseEntity<List<String>> getAllergy(Authentication authentication) {
        return ResponseEntity.ok(userService.getAllergy(authentication.getName()));
    }

    @GetMapping("/info/profile")
    public ResponseEntity<UserDto> getUserInfo(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserInfo(authentication.getName()));
    }
}
