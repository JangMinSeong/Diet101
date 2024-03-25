package com.d101.back.service;


import com.d101.back.dto.YoloResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class ModelService {


    private final WebClient webClient;


    public Mono<YoloResponseDto> transmitImageToYolo(MultipartFile file) throws IOException {
        byte[] imageData = file.getBytes();
        String imageName = "meal.png";
        Resource imageResource = new ByteArrayResource(imageData) {
            @Override
            public String getFilename() {
                return imageName;
            }
        };

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", imageResource)
                .filename(imageName)
                .contentType(MediaType.IMAGE_PNG);

        return webClient.post()
                .uri("/model/checkcal")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(YoloResponseDto.class)
                .doOnSuccess(response -> {
                    // YOLO 서버로부터 응답을 성공적으로 받았을 때 실행할 로직
                    System.out.println("이미지 업로드 성공: " + response);
                })
                .doOnError(error -> {
                    // 에러 처리 로직
                    System.err.println("에러 발생: " + error.getMessage());
                });
    }
}
