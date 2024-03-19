package com.d101.back.repository;

import com.d101.back.entity.Intake;
import com.d101.back.entity.composite.FoodMealKey;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IntakeRepository extends JpaRepository<Intake, FoodMealKey> {

}
