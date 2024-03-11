package com.d101.back.entity.composite;

import com.d101.back.entity.enums.AllergyType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAllergyKey implements Serializable {
    private Long user_id;

    @Enumerated(EnumType.STRING)
    private AllergyType allergy;
}
