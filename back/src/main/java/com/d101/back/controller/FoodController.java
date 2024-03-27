package com.d101.back.controller;

import com.d101.back.dto.FoodDto;
import com.d101.back.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/search")
    public ResponseEntity<List<FoodDto>> searchByName(@RequestParam String name) {
        List<FoodDto> list = foodService.searchByName(name);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<FoodDto>> rankingByEmail(Authentication authentication) {

        List<FoodDto> list = foodService.rankingByEmail(authentication.getName());
        return ResponseEntity.ok(list);
    }

    @PostMapping("/insert")
    public ResponseEntity<?> addFood(@RequestBody FoodDto foodDto) {

        foodService.addFood(foodDto);

        return ResponseEntity.ok("Success");
    }

    @GetMapping("/recommend")
    public ResponseEntity<?> getRecommendFood(Authentication authentication, @RequestParam(value = "kcal") String kcal) {
        foodService.getRecommend(authentication.getName(), Integer.parseInt(kcal));
        return ResponseEntity.ok(null);
    }

    @PostMapping("/db/unprocessedFood")
    public ResponseEntity<?> addUnprocessedFoodToDb(@RequestParam("file") MultipartFile file) throws Exception {

        foodService.unprocessedFood(file);

        return ResponseEntity.ok("Success");
    }

    @PostMapping("/db/processedFood")
    public ResponseEntity<?> addProcessedFoodToDb(@RequestParam("file") MultipartFile file) throws Exception {

        foodService.processedFood(file);

        return ResponseEntity.ok("Success");
    }
}
