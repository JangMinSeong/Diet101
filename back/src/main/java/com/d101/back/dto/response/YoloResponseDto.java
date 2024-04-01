package com.d101.back.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class YoloResponseDto {
    private String tag;
    private List<Double> left_top;
    private Double width;
    private Double height;
    private YoloFoodDto yoloFoodDto;
}
