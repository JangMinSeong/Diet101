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
	
}
