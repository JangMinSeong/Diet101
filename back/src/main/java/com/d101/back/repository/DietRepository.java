package com.d101.back.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.d101.back.entity.Meal;
import com.d101.back.entity.User;

public interface DietRepository extends JpaRepository<Meal, Long> {
	List<Meal> findByUserAndTime(User user, LocalDate date);
	List<Meal> findByUserAndTimeBetween(User user, LocalDate startDate, LocalDate endDate);
}
