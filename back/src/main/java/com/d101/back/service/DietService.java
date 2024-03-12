package com.d101.back.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.d101.back.entity.Meal;
import com.d101.back.entity.User;
import com.d101.back.exception.NoSuchDataException;
import com.d101.back.exception.UnAuthorizedException;
import com.d101.back.exception.response.ExceptionStatus;
import com.d101.back.repository.DietRepository;
import com.d101.back.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DietService {
	
	private final DietRepository dietRepository;
	private final UserRepository userRepository;
		
	public List<Meal> getMealsForSpecificDate(String email, String date) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.parse(date, formatter);
			return dietRepository.findByUserAndCreateDate(user.get(), localDate);
		} else {
			throw new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND);
		}
	}
	
	public List<Meal> getMealsForSpecificTerm(String email, String start, String end) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate startDate = LocalDate.parse(start, formatter);
			LocalDate endDate = LocalDate.parse(end, formatter);
			return dietRepository.findByUserAndCreateDateBetween(user.get(), startDate, endDate);
		} else {
			throw new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND);
		}
	}
	
	public Meal getMealById(Long id) {
		Optional<Meal> meal = dietRepository.findById(id);
		if (meal.isPresent()) {
			return meal.get();
		} else {
			throw new NoSuchDataException(ExceptionStatus.MEAL_NOT_FOUND);
		}
	}
	
	public Boolean isUserHaveMeal(String email, Meal meal) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			if (user.get().getMeals().contains(meal)) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND);
		}
	}
	
	public Meal getMealOfUserById(String email, Long id) {
		Meal meal = getMealById(id);
		if (isUserHaveMeal(email, meal)) {
			return meal;
		} else {
			throw new UnAuthorizedException(ExceptionStatus.UNAUTHORIZED);
		}
	}
}
