package com.d101.back.controller;

import com.d101.back.dto.response.SearchFoodRes;
import com.d101.back.service.FoodService;
import com.d101.back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/search")
    public ResponseEntity<List<SearchFoodRes>> searchByName(@RequestParam String name) {
        List<SearchFoodRes> list = foodService.searchByName(name);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<SearchFoodRes>> rankingByEmail(Authentication authentication) {

        List<SearchFoodRes> list = foodService.rankingByEmail(authentication.getName());
        return ResponseEntity.ok(list);
    }


}
