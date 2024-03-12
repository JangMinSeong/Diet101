package com.d101.back.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IntakeReq {
    private Long food_id;
    private int amount;
    private int kcal;
    private double carbohydrate;
    private double protein;
    private double fat;
}
