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

    public void uploadExcelFile(MultipartFile file) throws Exception {
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
            if (dbGroup != null && dbGroup.getStringCellValue() != "") {
                foodDto.setDbGroup(dbGroup.getStringCellValue());
            }

            Cell name = currentRow.getCell(1);
            if (name != null && name.getStringCellValue() != "") {
                foodDto.setName(name.getStringCellValue());
            }

            Cell manufacturer = currentRow.getCell(2);
            if (manufacturer != null && manufacturer.getStringCellValue() != "") {
                foodDto.setManufacturer(manufacturer.getStringCellValue());
            }

            Cell majorCategory = currentRow.getCell(3);
            if (majorCategory != null && majorCategory.getStringCellValue() != "") {
                foodDto.setMajorCategory(majorCategory.getStringCellValue());
            }

            Cell minorCategory = currentRow.getCell(4);
            if (minorCategory != null && minorCategory.getStringCellValue() != "") {
                foodDto.setMinorCategory(minorCategory.getStringCellValue());
            }

            Cell portionSize = currentRow.getCell(5);
            if (portionSize != null && portionSize.getStringCellValue() != "") {
                foodDto.setPortionSize(Integer.parseInt(portionSize.getStringCellValue()));
            }

            Cell unit = currentRow.getCell(6);
            if (unit != null && unit.getStringCellValue() != "") {
                foodDto.setUnit(unit.getStringCellValue());
            }

            Cell calorie = currentRow.getCell(7);
            if (calorie != null && calorie.getStringCellValue() != "") {
                foodDto.setCalorie((int)Double.parseDouble(calorie.getStringCellValue()));
            }

            Cell protein = currentRow.getCell(8);
            if (protein != null && protein.getStringCellValue() != "") {
                foodDto.setProtein(Double.parseDouble(protein.getStringCellValue()));
            }

            Cell fat = currentRow.getCell(9);
            if (fat != null && fat.getStringCellValue() != "") {
                foodDto.setFat(Double.parseDouble(fat.getStringCellValue()));
            }

            Cell carbohydrate = currentRow.getCell(10);
            if (carbohydrate != null && carbohydrate.getStringCellValue() != "") {
                foodDto.setCarbohydrate(Double.parseDouble(carbohydrate.getStringCellValue()));
            }

            Cell sugar = currentRow.getCell(11);
            if (sugar != null && sugar.getStringCellValue() != "") {
                foodDto.setSugar(Double.parseDouble(sugar.getStringCellValue()));
            }

            Cell natrium = currentRow.getCell(12);
            if (natrium != null && natrium.getStringCellValue() != "") {
                foodDto.setNatrium(Double.parseDouble(natrium.getStringCellValue()));
            }

            Cell cholesterol = currentRow.getCell(13);
            if (cholesterol != null && cholesterol.getStringCellValue() != "") {
                foodDto.setCholesterol(Double.parseDouble(cholesterol.getStringCellValue()));
            }

            Cell saturatedFat = currentRow.getCell(14);
            if (saturatedFat != null && saturatedFat.getStringCellValue() != "") {
                foodDto.setSaturatedFat(Double.parseDouble(saturatedFat.getStringCellValue()));
            }

            Cell transFat = currentRow.getCell(15);
            if (transFat != null && transFat.getStringCellValue() != "") {
                foodDto.setTransFat(Double.parseDouble(transFat.getStringCellValue()));
            }

            addFood(foodDto);
        }

        workbook.close();
    }

}
