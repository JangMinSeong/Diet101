package com.d101.back.controller;

import com.d101.back.dto.request.CreateOcrReq;
import com.d101.back.service.OCRService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ocr")
@RequiredArgsConstructor
public class OCRController {

    private final OCRService ocrService;

    @PostMapping("/save")
    public ResponseEntity<String> saveOCR (@RequestBody CreateOcrReq req, Authentication authentication) {
        ocrService.saveOCR(authentication.getName(), req);
        return ResponseEntity.ok("Success");
    }

}
