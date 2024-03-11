package com.d101.back.controller;

import com.d101.back.dto.LoginTokenDto;
import com.d101.back.dto.oauth.KakaoLoginReq;
import com.d101.back.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String url = s3Service.saveFile(file,"user/test");
        System.out.println(url);
        return ResponseEntity.ok(url);
    }
}
