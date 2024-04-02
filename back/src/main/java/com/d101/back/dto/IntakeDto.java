package com.d101.back.dto;

import com.d101.back.entity.Food;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class IntakeDto {
    private FoodDto food;
    private Double amount;

    @QueryProjection
    public IntakeDto(Food food, Double amount) {
        this.food = FoodDto.fromEntity(food);
        this.amount=amount;
    }

}
