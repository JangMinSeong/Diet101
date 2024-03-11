package com.d101.back.entity.composite;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.*;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFoodKey implements Serializable {
	private Long user_id;
	private Long food_id;
}
