package com.d101.back.controller;

import java.time.LocalDate;
import java.util.List;

import com.d101.back.dto.IntakeDto;
import com.d101.back.dto.MealDto;
import com.d101.back.dto.request.CreateMealReq;
import com.d101.back.dto.response.ResAnalysisDiet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.d101.back.entity.Meal;
import com.d101.back.service.DietService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/diet")
@RequiredArgsConstructor
public class DietController {
	private final DietService dietService;
	
	@GetMapping("")
	public ResponseEntity<?> getTodayDietInfo(Authentication authentication) {
		List<MealDto> meals = dietService.getMealsForSpecificDate(authentication.getName(), LocalDate.now().toString());
		return new ResponseEntity<>(meals, HttpStatus.OK);
	}
	
	@GetMapping("/date")
	public ResponseEntity<?> getDietInfoForDate(@RequestParam(value = "date") String date, Authentication authentication) {
		List<MealDto> meals = dietService.getMealsForSpecificDate(authentication.getName(), date);
		return new ResponseEntity<>(meals, HttpStatus.OK);
	}
	
	@GetMapping("/term")
	public ResponseEntity<?> getDietInfoForTerm(@RequestParam(value = "dateFrom") String dateFrom, @RequestParam(value = "dateTo") String dateTo, Authentication authentication) {
		List<MealDto> meals = dietService.getMealsForSpecificTerm(authentication.getName(), dateFrom, dateTo);
		return new ResponseEntity<>(meals, HttpStatus.OK);
	}

	@PostMapping("/meal")
	public ResponseEntity<?> saveMeal(Authentication authentication, @RequestPart MultipartFile file, @RequestPart CreateMealReq req) {
		dietService.saveMeal(authentication.getName(), file, req);
		return ResponseEntity.ok("Success");
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getDietDetail(@PathVariable(value = "id") Long id, Authentication authentication) {
		MealDto meal = dietService.getMealOfUserById(authentication.getName(), id);
		return new ResponseEntity<>(meal, HttpStatus.OK);
	}

	@GetMapping("/{meal_id}/{food_id}")
	public ResponseEntity<?> getFoodFromDiet(@PathVariable(value = "meal_id") Long meal_id, @PathVariable(value = "food_id") Long food_id, Authentication authentication) {
		IntakeDto intake = dietService.getFoodDto(meal_id, food_id, authentication.getName());
		return new ResponseEntity<>(intake, HttpStatus.OK);
	}

	@GetMapping("/analysis")
	public ResponseEntity<?> getAnalysisDiet(@RequestParam(value = "date") String date, @RequestParam(value = "dateFrom") String dateFrom, @RequestParam(value = "dateTo") String dateTo, Authentication authentication) {
		ResAnalysisDiet result = dietService.analysisDiet(date,dateFrom,dateTo,authentication.getName());

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
