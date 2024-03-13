package com.d101.back.dto;

import com.d101.back.entity.Food;
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

    public static FoodDto fromEntity (Food food) {
        return new FoodDto(
                food.getId(),
                food.getName(),
                food.getCalorie(),
                food.getCarbohydrate(),
                food.getProtein(),
                food.getFat(),
                food.getTransFat(),
                food.getSaturatedFat(),
                food.getCholesterol(),
                food.getNatrium(),
                food.getSugar()
        );
    }
}