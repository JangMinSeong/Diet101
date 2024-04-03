package com.d101.back.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.d101.back.entity.Meal;
import com.d101.back.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DietRepository extends JpaRepository<Meal, Long> {
	List<Meal> findByUserAndTime(User user, LocalDate date);
	List<Meal> findByUserAndTimeBetween(User user, LocalDate startDate, LocalDate endDate);

	@Query("SELECT FUNCTION('month', meal.time) AS month, SUM(meal.totalCalorie) AS totalCalorie, SUM(meal.totalCarbohydrate) AS totalCarbohydrate,  SUM(meal.totalProtein) AS totalProtein,SUM(meal.totalFat) AS totalFat FROM Meal meal WHERE meal.user.email = :email AND FUNCTION('year', meal.time) = FUNCTION('year', CURRENT_DATE) GROUP BY FUNCTION('month', meal.time)")
	List<Object[]> findAnnualNutritionByEmail(@Param("email") String email);
}
