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
    private double sugar;
    private double protein;
    private double fat;
    private double saturated;
    private double trans;
    private double cholesterol;
    private double sodium;
}
