package com.d101.back.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.d101.back.entity.Meal;
import com.d101.back.entity.User;
import com.d101.back.exception.NoSuchDataException;
import com.d101.back.exception.response.ExceptionStatus;
import com.d101.back.repository.DietRepository;
import com.d101.back.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DietService {
	
	private final DietRepository dietRepository;
	private final UserRepository userRepository;
		
	public List<Meal> getMealsForSpecificDate(String email, LocalDate localDate) {
		Optional<User> user = this.userRepository.findByEmail(email);
		if (user.isPresent()) {
			return this.dietRepository.findByUserAndCreateDate(user.get(), localDate);
		} else {
			throw new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND);
		}
	}
}
