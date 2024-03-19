package com.d101.back.dto;

import com.d101.back.entity.enums.Dunchfast;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MealDto {
    private Long meal_id;
    private String image;
    private String time;
    private Dunchfast type;
    private int kcal;
    private List<IntakeDto> intake;
}
