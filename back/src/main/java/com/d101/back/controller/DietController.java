package com.d101.back.controller;

import java.time.LocalDate;
import java.util.List;

import com.d101.back.dto.MealDto;
import com.d101.back.dto.request.CreateMealReq;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.d101.back.service.DietService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/diet")
@RequiredArgsConstructor
public class DietController {
	private final DietService dietService;
	
	@GetMapping("/info")
	public ResponseEntity<?> getTodayDietInfo(Authentication authentication) {
		List<MealDto> meals = dietService.getMealsForSpecificDate(authentication.getName(), LocalDate.now().toString());
		return new ResponseEntity<>(meals, HttpStatus.OK);
	}
	
	@GetMapping("/info/date")
	public ResponseEntity<?> getDietInfoForDate(@RequestParam(value = "date") String date, Authentication authentication) {
		List<MealDto> meals = dietService.getMealsForSpecificDate(authentication.getName(), date);
		return new ResponseEntity<>(meals, HttpStatus.OK);
	}
	
	@GetMapping("/info/term")
	public ResponseEntity<?> getDietInfoForTerm(@RequestParam(value = "dateFrom") String dateFrom, @RequestParam(value = "dateTo") String dateTo, Authentication authentication) {
		List<MealDto> meals = dietService.getMealsForSpecificTerm(authentication.getName(), dateFrom, dateTo);
		return new ResponseEntity<>(meals, HttpStatus.OK);
	}

	@PostMapping("/meal")
	public ResponseEntity<?> saveMeal(Authentication authentication, @RequestBody CreateMealReq req) {
		dietService.saveMeal(authentication.getName(),  req);
		return ResponseEntity.ok("Success");
	}

	@GetMapping("/info/id")
	public ResponseEntity<?> getDietDetail(@RequestParam(value = "id") Long id, Authentication authentication) {
		MealDto meal = dietService.getMealOfUserById(authentication.getName(), id);
		return new ResponseEntity<>(meal, HttpStatus.OK);
	}
}
