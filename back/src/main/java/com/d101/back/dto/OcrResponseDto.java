package com.d101.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class OcrResponseDto {

    private List<OcrDto> OcrResponse;

    public static class OcrDto {
        private String nutrient;
        private double number;
        private String unit;
    }
}
