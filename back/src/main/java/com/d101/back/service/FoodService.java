package com.d101.back.service;

import com.d101.back.dto.FoodDto;
import com.d101.back.entity.Food;
import com.d101.back.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final WebClient webClient;

    @Transactional(readOnly = true)
    public List<FoodDto> searchByName(String name) {
        return foodRepository.findByNameContainingOrderByNameAsc(name, PageRequest.of(0, 30)).stream()
                .map(FoodDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FoodDto> rankingByEmail(String email) {
        return foodRepository.rankingByEmail(email, PageRequest.of(0, 10)).stream()
                .map(FoodDto::fromEntity)
                .toList();
    }

    @Transactional
    public void addFood(FoodDto foodDto) {
        Food food = Food.builder()
                .calorie(foodDto.getCalorie())
                .carbohydrate(foodDto.getCarbohydrate())
                .protein(foodDto.getProtein())
                .fat(foodDto.getFat())
                .name(foodDto.getName())
                .cholesterol(foodDto.getCholesterol())
                .sugar(foodDto.getSugar())
                .natrium(foodDto.getNatrium())
                .saturatedFat(foodDto.getSaturatedFat())
                .transFat(foodDto.getTransFat())
                .majorCategory(foodDto.getMajorCategory())
                .minorCategory(foodDto.getMinorCategory())
                .dbGroup(foodDto.getDbGroup())
                .manufacturer(foodDto.getManufacturer())
                .build();
        foodRepository.save(food);
    }

    public void getRecommend(String email, int kcal) {
        Mono<String> response = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/recommend")
                        .queryParam("email", email)
                        .queryParam("kcal", kcal)
                        .build())
                .retrieve().bodyToMono(String.class);
        response.subscribe(log::info);
    }

}
