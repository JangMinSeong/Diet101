package com.d101.back.entity;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter
@Entity
public class Food extends BaseTimeEntity {

	@Id
	@Column(name="food_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;

	private String majorCategory;
	private String minorCategory;

	private String dbGroup;
	private String manufacturer;

	private int portionSize;
	private double totalSize;
	private String unit;

	private int calorie;
	private double carbohydrate;
	private double protein;
	private double fat;
	private double transFat;
	private double saturatedFat;
	private double cholesterol;
	private double natrium;
	private double sugar;

	@Builder
	public Food(String name, String majorCategory, String minorCategory, String dbGroup, String manufacturer, int portionSize, double totalSize, String unit, int calorie, double carbohydrate, double protein, double fat, double transFat, double saturatedFat, double cholesterol, double natrium, double sugar) {
		this.name = name;
		this.majorCategory = majorCategory;
		this.minorCategory = minorCategory;
		this.dbGroup = dbGroup;
		this.manufacturer = manufacturer;
		this.portionSize = portionSize;
		this.totalSize = totalSize;
		this.unit = unit;
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
