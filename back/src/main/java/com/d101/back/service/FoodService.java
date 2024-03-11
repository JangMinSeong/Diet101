package com.d101.back.service;

import com.d101.back.dto.response.SearchFoodRes;
import com.d101.back.entity.Food;
import com.d101.back.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

    @Transactional
    public List<SearchFoodRes> searchByName(String name) {
        return foodRepository.findByNameContainingOrderByNameAsc(name, PageRequest.of(0, 30)).stream()
                .map(temp -> {
                    SearchFoodRes input = new SearchFoodRes();
                    BeanUtils.copyProperties(temp, input);
                    return input;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public List<SearchFoodRes> rankingByEmail(String email) {
        return foodRepository.rankingByEmail(email, PageRequest.of(0, 10)).stream()
                .map(temp -> {
                    SearchFoodRes input = new SearchFoodRes();
                    BeanUtils.copyProperties(temp, input);
                    return input;
                })
                .collect(Collectors.toList());
    }
}
