package com.d101.back.repository;

import com.d101.back.entity.Allergy;
import com.d101.back.entity.composite.UserAllergyKey;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AllergyRepository extends JpaRepository<Allergy, UserAllergyKey> {


}
