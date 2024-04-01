package com.d101.back.service;

import com.d101.back.dto.OCRDto;
import com.d101.back.dto.request.CreateOcrReq;
import com.d101.back.entity.OCR;
import com.d101.back.entity.User;
import com.d101.back.exception.NoSuchDataException;
import com.d101.back.exception.response.ExceptionStatus;
import com.d101.back.repository.OCRRepository;
import com.d101.back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OCRService {

    private final OCRRepository ocrRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveOCR(String email, CreateOcrReq req) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND));
        ocrRepository.save(OCR.of(user,req));
    }

    public List<OCRDto> getDateOCR(String email, String date){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return  ocrRepository.findAllByUserAndTime(user,localDate)
                .stream().map(OCRDto::fromEntity)
                .toList();
    }
}
