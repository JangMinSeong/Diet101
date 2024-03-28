package com.d101.back.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CalAnnualNutrient {
    private int month;
    private int totalCalorie;
    private double totalCarbohydrate;
    private double totalProtein;
    private double totalFat;
}
