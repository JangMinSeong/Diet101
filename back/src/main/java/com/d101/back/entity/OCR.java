package com.d101.back.entity;

import com.d101.back.dto.request.CreateOcrReq;
import com.d101.back.entity.enums.Dunchfast;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class OCR {

    @Id
    @Column(name = "ocr_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Dunchfast type;

    private LocalDate time;
    private String name;
    private int calorie;
    private double carbohydrate;
    private double protein;
    private double fat;

    public static OCR of (User user, CreateOcrReq req) {
        OCR ocr = new OCR();
        ocr.setUser(user);
        ocr.setType(Dunchfast.valueOf(req.getType()));
        ocr.setTime(req.getTime());
        ocr.setName(req.getName());
        ocr.setCalorie(req.getCalorie());
        ocr.setCarbohydrate(req.getCarbohydrate());
        ocr.setProtein(req.getProtein());
        ocr.setFat(req.getFat());
        return ocr;
    }
}
