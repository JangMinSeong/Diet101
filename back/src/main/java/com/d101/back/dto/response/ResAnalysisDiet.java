package com.d101.back.dto.response;

import com.d101.back.dto.FoodDto;
import com.d101.back.dto.MealDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResAnalysisDiet {
    private List<MealDto> dailyDiet;
    private List<MealDto> weeklyDiet;
    private List<CalAnnualNutrient> annualNutrients;
    private List<String> totalRank;
}
