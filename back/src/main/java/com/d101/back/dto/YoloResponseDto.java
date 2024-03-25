package com.d101.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class YoloResponseDto {

    private List<YoloDto> YoloResponse;

    public static class YoloDto {

        private String tag;
        private List<Double> left_top;
        private double width;
        private double height;
    }
}
