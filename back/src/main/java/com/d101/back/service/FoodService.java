package com.d101.back.service;

import com.d101.back.dto.FoodDto;
import com.d101.back.entity.Food;
import com.d101.back.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.Iterator;
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
                .portionSize(foodDto.getPortionSize())
                .unit(foodDto.getUnit())
                .totalSize(foodDto.getTotalSize())
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

    public void unprocessedFood(MultipartFile file) throws Exception {
        InputStream in = file.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(in);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();

        if (rows.hasNext()) {
            rows.next();
        }

        while (rows.hasNext()) {
            Row currentRow = rows.next();
            FoodDto foodDto = new FoodDto();


            Cell dbGroup = currentRow.getCell(0);
            if (dbGroup != null) {
                foodDto.setDbGroup(dbGroup.getStringCellValue());
            }

            Cell name = currentRow.getCell(1);
            if (name != null) {
                foodDto.setName(name.getStringCellValue());
            }

            Cell manufacturer = currentRow.getCell(2);
            if (manufacturer != null) {
                foodDto.setManufacturer(manufacturer.getStringCellValue());
            }

            Cell majorCategory = currentRow.getCell(3);
            if (majorCategory != null) {
                foodDto.setMajorCategory(majorCategory.getStringCellValue());
            }

            Cell minorCategory = currentRow.getCell(4);
            if (minorCategory != null) {
                foodDto.setMinorCategory(minorCategory.getStringCellValue());
            }

            Cell portionSize = currentRow.getCell(5);
            try {
                foodDto.setPortionSize(Integer.parseInt(portionSize.getStringCellValue()));
            } catch (NumberFormatException e) {

            }

            Cell unit = currentRow.getCell(6);
            if (unit != null) {
                foodDto.setUnit(unit.getStringCellValue());
            }

            try {
                Cell totalSize = currentRow.getCell(7);
                foodDto.setTotalSize(Double.parseDouble(totalSize.getStringCellValue()));
            } catch (NumberFormatException e1) {
                try {
                    Cell totalSize = currentRow.getCell(8);
                    foodDto.setTotalSize(Double.parseDouble(totalSize.getStringCellValue()));
                } catch (NumberFormatException e2) {

                }
            }

            Cell calorie = currentRow.getCell(9);
            try {
                foodDto.setCalorie((int)Double.parseDouble(calorie.getStringCellValue()));
            } catch (NumberFormatException e) {

            }

            Cell protein = currentRow.getCell(10);
            try {
                foodDto.setProtein(Double.parseDouble(protein.getStringCellValue()));
            } catch (NumberFormatException e) {

            }

            Cell fat = currentRow.getCell(11);
            try {
                foodDto.setFat(Double.parseDouble(fat.getStringCellValue()));
            } catch (NumberFormatException e) {

            }

            Cell carbohydrate = currentRow.getCell(12);
            try {
                foodDto.setCarbohydrate(Double.parseDouble(carbohydrate.getStringCellValue()));
            } catch (NumberFormatException e) {

            }

            Cell sugar = currentRow.getCell(13);
            try {
                foodDto.setSugar(Double.parseDouble(sugar.getStringCellValue()));
            } catch (NumberFormatException e) {

            }

            Cell natrium = currentRow.getCell(14);
            try {
                foodDto.setNatrium(Double.parseDouble(natrium.getStringCellValue()));
            } catch (NumberFormatException e) {

            }

            Cell cholesterol = currentRow.getCell(15);
            try {
                foodDto.setCholesterol(Double.parseDouble(cholesterol.getStringCellValue()));
            } catch (NumberFormatException e) {

            }

            Cell saturatedFat = currentRow.getCell(16);
            try {
                foodDto.setSaturatedFat(Double.parseDouble(saturatedFat.getStringCellValue()));
            } catch (NumberFormatException e) {

            }

            Cell transFat = currentRow.getCell(17);
            try {
                foodDto.setTransFat(Double.parseDouble(transFat.getStringCellValue()));
            } catch (NumberFormatException e) {

            }

            addFood(foodDto);
        }

        workbook.close();
    }

    public void processedFood(MultipartFile file) throws Exception {
        InputStream in = file.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(in);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();

        // 첫 번째 행은 제목이므로 건너뜁니다.
        if (rows.hasNext()) {
            rows.next();
        }

        while (rows.hasNext()) {
            Row currentRow = rows.next();
            FoodDto foodDto = new FoodDto();


            Cell dbGroup = currentRow.getCell(0);
            if (dbGroup != null) {
                foodDto.setDbGroup(dbGroup.getStringCellValue());
            }

            Cell name = currentRow.getCell(1);
            if (name != null) {
                foodDto.setName(name.getStringCellValue());
            }

            Cell manufacturer = currentRow.getCell(2);
            if (manufacturer != null) {
                foodDto.setManufacturer(manufacturer.getStringCellValue());
            }

            Cell majorCategory = currentRow.getCell(3);
            if (majorCategory != null) {
                foodDto.setMajorCategory(majorCategory.getStringCellValue());
            }

            Cell minorCategory = currentRow.getCell(4);
            if (minorCategory != null) {
                foodDto.setMinorCategory(minorCategory.getStringCellValue());
            }

            Cell portionSize = currentRow.getCell(5);
            try {
                foodDto.setPortionSize(Integer.parseInt(portionSize.getStringCellValue()));
            } catch (NumberFormatException e) {

            }

            Cell unit = currentRow.getCell(6);
            if (unit != null) {
                foodDto.setUnit(unit.getStringCellValue());
            }

            try {
                Cell totalSize = currentRow.getCell(7);
                foodDto.setTotalSize(Double.parseDouble(totalSize.getStringCellValue()));
            } catch (NumberFormatException e1) {
                try {
                    Cell totalSize = currentRow.getCell(8);
                    foodDto.setTotalSize(Double.parseDouble(totalSize.getStringCellValue()));
                } catch (NumberFormatException e2) {

                }
            }

            Cell calorie = currentRow.getCell(9);
            try {
                foodDto.setCalorie((int)Double.parseDouble(calorie.getStringCellValue()));
            } catch (NumberFormatException e) {

            }

            Cell protein = currentRow.getCell(10);
            try {
                foodDto.setProtein(Double.parseDouble(protein.getStringCellValue()));
            } catch (NumberFormatException e) {

            }

            Cell fat = currentRow.getCell(11);
            try {
                foodDto.setFat(Double.parseDouble(fat.getStringCellValue()));
            } catch (NumberFormatException e) {

            }

            Cell carbohydrate = currentRow.getCell(12);
            try {
                foodDto.setCarbohydrate(Double.parseDouble(carbohydrate.getStringCellValue()));
            } catch (NumberFormatException e) {

            }

            Cell sugar = currentRow.getCell(13);
            try {
                foodDto.setSugar(Double.parseDouble(sugar.getStringCellValue()));
            } catch (NumberFormatException e) {

            }

            Cell natrium = currentRow.getCell(14);
            try {
                foodDto.setNatrium(Double.parseDouble(natrium.getStringCellValue()));
            } catch (NumberFormatException e) {

            }

            try {
                Cell cholesterol = currentRow.getCell(15);
                foodDto.setCholesterol(Double.parseDouble(cholesterol.getStringCellValue()));
            } catch (NumberFormatException e1) {
                try {
                    Cell cholesterol = currentRow.getCell(16);
                    foodDto.setCholesterol(Double.parseDouble(cholesterol.getStringCellValue()));
                } catch (NumberFormatException e2) {

                }
            }

            Cell saturatedFat = currentRow.getCell(17);
            try {
                foodDto.setSaturatedFat(Double.parseDouble(saturatedFat.getStringCellValue()));
            } catch (NumberFormatException e) {

            }

            Cell transFat = currentRow.getCell(18);
            try {
                foodDto.setTransFat(Double.parseDouble(transFat.getStringCellValue()));
            } catch (NumberFormatException e) {

            }

            addFood(foodDto);
        }

        workbook.close();
    }

}
