package com.d101.back.service;


import com.d101.back.dto.response.OcrResponse;
import com.d101.back.dto.response.YoloFoodDto;
import com.d101.back.dto.response.YoloResponseDto;
import com.d101.back.exception.NoSuchDataException;
import com.d101.back.exception.response.ExceptionStatus;
import com.d101.back.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ModelService {

    @Value("${gpu.server.url}")
    private String url;

    private final FoodRepository foodRepository;

    public YoloResponseDto[] transmitImageToYolo(MultipartFile multipartFile) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        Resource resource = new ByteArrayResource(multipartFile.getBytes()) {
            @Override
            public String getFilename() {
                return multipartFile.getOriginalFilename();
            }
        };

        body.add("file", resource); // 'image'는 서버에서 파일을 받는 파라미터명과 일치해야 함

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<YoloResponseDto[]> response = restTemplate.postForEntity(url + "/model/checkcal", requestEntity, YoloResponseDto[].class);

        for (YoloResponseDto res : response.getBody()) {
            try {
                YoloFoodDto food = YoloFoodDto.fromEntity(foodRepository.findByName(res.getTag())
                        .orElseThrow(() -> new NoSuchDataException(ExceptionStatus.FOOD_NOT_FOUND)));
                res.setYoloFoodDto(food);
            } catch (NoSuchDataException e) {

            }
        }

        return response.getBody();
    }

    public OcrResponse transmitImageToOCR(MultipartFile multipartFile) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        Resource resource = new ByteArrayResource(multipartFile.getBytes()) {
            @Override
            public String getFilename() {
                return multipartFile.getOriginalFilename();
            }
        };

        body.add("file", resource); // 'image'는 서버에서 파일을 받는 파라미터명과 일치해야 함

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<OcrResponse> response =
                restTemplate.postForEntity(url + "/model/ocr", requestEntity, OcrResponse.class);
        return response.getBody();
    }
}
