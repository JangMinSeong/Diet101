package com.d101.back.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.d101.back.dto.QIntakeDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.d101.back.dto.IntakeDto;
import com.d101.back.dto.MealDto;
import com.d101.back.dto.request.CreateMealReq;
import com.d101.back.entity.Intake;
import com.d101.back.entity.Meal;
import com.d101.back.entity.User;
import com.d101.back.entity.QIntake;
import com.d101.back.entity.QMeal;
import com.d101.back.entity.QFood;
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
	
	public List<MealDto> getMealsForSpecificTerm(String email, String start, String end) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate = LocalDate.parse(start, formatter);
		LocalDate endDate = LocalDate.parse(end, formatter);
		List<Meal> meals = dietRepository.findByUserAndTimeBetween(user, startDate, endDate);
		
		QMeal meal = QMeal.meal;
		QIntake intake = QIntake.intake;
		QFood food = QFood.food;
		return meals.stream().map(m -> {
			List<IntakeDto> intakeData = jpaQueryFactory
					.select(new QIntakeDto(food, intake.amount)).distinct()
					.from(meal, intake, food)
					.where(intake.key.meal_id.eq(m.getId()), intake.key.food_id.eq(food.id))
					.fetch();
			return new MealDto(m.getId(), m.getImage(), m.getTime().toString(), m.getType(), m.getTotalCalorie(), intakeData);
		}).toList();
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
        return dietRepository.findById(id)
				.orElseThrow(() -> new NoSuchDataException(ExceptionStatus.MEAL_NOT_FOUND));
	}
	
	public Boolean isUserHaveMeal(String email, Meal meal) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND));
        return user.getMeals().contains(meal);
    }
	
	public MealDto getMealOfUserById(String email, Long id) {
		Meal meal = getMealById(id);
		
		if (isUserHaveMeal(email, meal)) {
			QMeal qMeal = QMeal.meal;
			QIntake intake = QIntake.intake;
			QFood food = QFood.food;
			List<IntakeDto> intakeData = jpaQueryFactory
					.select(new QIntakeDto(food, intake.amount)).distinct()
					.from(qMeal, intake, food)
					.where(intake.key.meal_id.eq(meal.getId()), intake.key.food_id.eq(food.id))
					.fetch(); 
			return new MealDto(meal.getId(), meal.getImage(), meal.getTime().toString(), meal.getType(), meal.getTotalCalorie(), intakeData);
		}
		throw new UnAuthorizedException(ExceptionStatus.UNAUTHORIZED);
	}

	public IntakeDto getFoodDto(MealDto meal, Long food_id) {
		List<IntakeDto> intake = meal.getIntake().stream()
				.filter(i -> i.getFood().getId().equals(food_id)).toList();
		if (!intake.isEmpty()) {
			return intake.getFirst();
		}
		throw new NoSuchDataException(ExceptionStatus.Food_NOT_FOUND);
	}
}
