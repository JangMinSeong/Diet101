package com.d101.back.repository;

import com.d101.back.entity.OCR;
import com.d101.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OCRRepository extends JpaRepository<OCR, Long> {
    List<OCR> findAllByUserAndTime(User user, LocalDate date);
}
