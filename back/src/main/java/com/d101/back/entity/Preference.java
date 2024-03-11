package com.d101.back.entity;

import com.d101.back.entity.composite.UserFoodKey;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter 
@Setter
@Entity
public class Preference {
	@EmbeddedId
	private UserFoodKey key;
	
	private int weight;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "food_id")
	private Food food;
}
