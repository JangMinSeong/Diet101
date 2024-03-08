package com.d101.back.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter
@Entity
public class Food extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private int calorie;
	private int carbohydrate;
	private int protein;
	private int fat;
	private int transFat;
	private int saturatedFat;
	private int cholesterol;
	private int natrium;
	private int sugar;
	
	@OneToMany(mappedBy = "food")
	private List<Preference> preferences;
	
	@Builder
	public Food(String name, int calorie, int carbohydrate, int protein, int fat, int transFat, int saturatedFat, int cholesterol, int natrium, int sugar) {
		this.name = name;
		this.calorie = calorie;
		this.carbohydrate = carbohydrate;
		this.protein = protein;
		this.fat = fat;
		this.transFat = transFat;
		this.saturatedFat = saturatedFat;
		this.cholesterol = cholesterol;
		this.natrium = natrium;
		this.sugar = sugar;
	}
	
}
