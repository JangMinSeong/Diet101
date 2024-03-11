package com.d101.back.entity.composite;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.*;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodMealKey implements Serializable {
	private Long food_id;
	private Long meal_id;
}
