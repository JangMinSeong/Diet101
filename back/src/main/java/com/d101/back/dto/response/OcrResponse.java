package com.d101.back.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class OcrResponse {
    private int calorie;
    private double carbohydrate;
    private double protein;
    private double fat;
}
