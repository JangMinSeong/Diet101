package com.d101.back.dto;

import com.d101.back.entity.OCR;
import com.d101.back.entity.enums.Dunchfast;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OCRDto {
    private String name;
    private Dunchfast type;
    private LocalDate time;
    private int calorie;
    private double carbohydrate;
    private double protein;
    private double fat;

    static public OCRDto fromEntity(OCR entity) {
        return new OCRDto(
                entity.getName(),
                entity.getType(),
                entity.getTime(),
                entity.getCalorie(),
                entity.getCarbohydrate(),
                entity.getProtein(),
                entity.getFat()
        );
    }
}
