package com.d101.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FoodDto {
    private Long id;

    private String name;

    private int calorie;
    private double carbohydrate;
    private double protein;
    private double fat;
    private double transFat;
    private double saturatedFat;
    private double cholesterol;
    private double natrium;
    private double sugar;

}
