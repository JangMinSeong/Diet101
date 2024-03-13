package com.d101.back.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.d101.back.dto.IntakeDto;
import com.d101.back.dto.MealDto;
import com.d101.back.dto.QIntakeDto;
import com.d101.back.dto.request.CreateMealReq;
import com.d101.back.entity.Intake;
import com.d101.back.entity.Meal;
import com.d101.back.entity.User;
import com.d101.back.entity.composite.FoodMealKey;
import com.d101.back.entity.enums.Dunchfast;
import com.d101.back.exception.NoSuchDataException;
import com.d101.back.exception.UnAuthorizedException;
import com.d101.back.exception.response.ExceptionStatus;
import com.d101.back.repository.DietRepository;
import com.d101.back.repository.IntakeRepository;
import com.d101.back.repository.UserRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DietService {
	
	private final DietRepository dietRepository;
	private final UserRepository userRepository;
	private final IntakeRepository intakeRepository;
	private final JPAQueryFactory jpaQueryFactory;
		
	public List<MealDto> getMealsForSpecificDate(String email, String date) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		QMeal meal = QMeal.meal;
		QIntake intake = QIntake.intake;
		QFood food = QFood.food;
		// 그날 먹은 식사 가져오기
		List<Meal> meals = dietRepository.findByUserAndTime(user, localDate);
		return meals.stream().map(m -> {
			List<IntakeDto> intakeData = jpaQueryFactory
					.select(new QIntakeDto(food, intake.amount)).distinct()
					.from(meal, intake, food)
					.where(intake.key.meal_id.eq(m.getId()), intake.key.food_id.eq(food.id))
					.fetch();
			return new MealDto(m.getId(), m.getImage(), date, m.getType(), m.getTotalCalorie(), intakeData);
		}).toList();
	}
	
	public List<Meal> getMealsForSpecificTerm(String email, String start, String end) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate startDate = LocalDate.parse(start, formatter);
			LocalDate endDate = LocalDate.parse(end, formatter);
			return dietRepository.findByUserAndTimeBetween(user.get(), startDate, endDate);
		} else {
			throw new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND);
		}
	}

	@Transactional
	public void saveMeal(String email, CreateMealReq req) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND));

		Meal meal = new Meal(user, req.getImage(), Dunchfast.valueOf(req.getType()), req.getTime(),
				req.getTotalKcal(), req.getTotalCarbohydrate(),
				req.getTotalProtein(), req.getTotalFat());
		dietRepository.save(meal);

		List<Intake> intakes = req.getIntakes().stream()
				.map(intake -> new Intake(new FoodMealKey(intake.getFood_id(), meal.getId()), intake.getAmount()))
				.toList();
		intakeRepository.saveAll(intakes);
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
