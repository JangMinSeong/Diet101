package com.d101.back.dto.response;


import com.d101.back.entity.Food;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class YoloFoodDto {
    private Long food_id;
    private int calorie;
    private double carbohydrate;
    private double protein;
    private double fat;

    public static YoloFoodDto fromEntity (Food food) {
        return new YoloFoodDto(
                food.getId(),
                food.getCalorie(),
                food.getCarbohydrate(),
                food.getProtein(),
                food.getFat()
                );
    }
}
