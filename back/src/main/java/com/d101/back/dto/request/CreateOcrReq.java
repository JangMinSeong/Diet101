package com.d101.back.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOcrReq {
    private String name;
    private String type;
    private String time;
    private int calorie;
    private double carbohydrate;
    private double protein;
    private double fat;
    public LocalDate getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(time, formatter);
    }
}
