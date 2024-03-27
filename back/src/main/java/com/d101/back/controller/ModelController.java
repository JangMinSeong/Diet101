package com.d101.back.controller;


import com.d101.back.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/model")
@RequiredArgsConstructor
public class ModelController {

    private final ModelService modelService;

    @PostMapping("/checkcal")
    public ResponseEntity<?> imageToYoloAi(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(modelService.transmitImageToYolo(file));
    }

    @PostMapping("/ocr")
    public ResponseEntity<?> imageToOcrAi(@RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(modelService.transmitImageToOCR(file));
    }
}
