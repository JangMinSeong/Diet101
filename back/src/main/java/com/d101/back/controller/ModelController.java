package com.d101.back.controller;


import com.d101.back.dto.YoloResponseDto;
import com.d101.back.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping("/api/model")
@RequiredArgsConstructor
public class ModelController {

    private final ModelService modelService;

    @PostMapping("/checkcal")
    public ResponseEntity<?> imageAi(@RequestPart("file") MultipartFile file) throws IOException {
        Mono<YoloResponseDto> YoloResponse = modelService.transmitImageToYolo(file);
        return ResponseEntity.ok(YoloResponse);
    }
}
