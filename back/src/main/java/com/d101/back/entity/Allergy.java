package com.d101.back.entity;

import com.d101.back.entity.composite.UserAllergyKey;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Allergy {
    @EmbeddedId
    private UserAllergyKey key;
}


