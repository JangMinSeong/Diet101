package com.d101.back.entity;

import com.d101.back.entity.composite.FoodMealKey;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Intake {
	@EmbeddedId
	private FoodMealKey key;
	
	private int amount;
	
	@ManyToOne
	@JoinColumn(name = "food_id")
	private Food food;
	
	@ManyToOne
	@JoinColumn(name = "meal_id")
	private Meal meal;
	
}
