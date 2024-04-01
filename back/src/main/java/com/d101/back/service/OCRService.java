package com.d101.back.service;

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
}
