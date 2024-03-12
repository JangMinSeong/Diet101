package com.d101.back.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.d101.back.entity.Meal;
import com.d101.back.service.DietService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/diet")
@RequiredArgsConstructor
public class DietController {
	private final DietService dietService;
	
	@GetMapping("/info")
	public ResponseEntity<?> getTodayDietInfo(Authentication authentication) {
		List<Meal> meals = this.dietService.getMealsForSpecificDate(authentication.getName(), LocalDate.now());
		return new ResponseEntity<>(meals, HttpStatus.OK);
	}
	
	@GetMapping("/info/{day}")
	public ResponseEntity<?> getDietInfo(@PathVariable("day") LocalDate localDate, Authentication authentication) {
		List<Meal> meals = this.dietService.getMealsForSpecificDate(authentication.getName(), localDate);
		return new ResponseEntity<>(meals, HttpStatus.OK);
	}
}
