package com.d101.back.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMealReq {

    private String type;
    private String time;
    private List<IntakeReq> intakes;

    public LocalDate getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
       return LocalDate.parse(time, formatter);
    }

    public int getTotalKcal() {
        return this.intakes.stream()
                .mapToInt(intakes -> (int)(intakes.getKcal() * intakes.getAmount()))
                .sum();
    }

    public double getTotalCarbohydrate() {
        return this.intakes.stream()
                .mapToDouble(intake -> intake.getCarbohydrate() * intake.getAmount())
                .sum();
    }

    public double getTotalProtein() {
        return this.intakes.stream()
                .mapToDouble(intake -> intake.getProtein() * intake.getProtein())
                .sum();
    }

    public double getTotalFat() {
        return this.intakes.stream()
                .mapToDouble(intake -> intake.getFat() * intake.getAmount())
                .sum();
    }

}
