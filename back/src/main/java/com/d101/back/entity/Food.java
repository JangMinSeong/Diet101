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
	private double carbohydrate;
	private double protein;
	private double fat;
	private double transFat;
	private double saturatedFat;
	private double cholesterol;
	private double natrium;
	private double sugar;
	
	@OneToMany(mappedBy = "food")
	private List<Preference> preferences;
	
	@OneToMany(mappedBy = "food")
	private List<Intake> intakes;
	
	@Builder
	public Food(String name, int calorie, double carbohydrate, double protein, double fat, double transFat, double saturatedFat, double cholesterol, double natrium, double sugar) {
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
