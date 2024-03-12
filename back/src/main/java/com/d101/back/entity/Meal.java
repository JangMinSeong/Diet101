package com.d101.back.entity;

import java.time.LocalDate;

import com.d101.back.entity.enums.Dunchfast;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Meal extends  BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    @Enumerated(EnumType.STRING)
    private Dunchfast type;

    private int totalCalorie;
    private double totalCarbohydrate;
    private double totalProtein;
    private double totalFat;
    
    private LocalDate time;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public Meal(User user, String image, Dunchfast type, LocalDate time, int totalCalorie, double totalCarbohydrate, double totalProtein, double totalFat)  {
        this.user = user;
        this.image = image;
        this.type = type;
        this.time = time;
        this.totalCalorie = totalCalorie;
        this.totalCarbohydrate = totalCarbohydrate;
        this.totalProtein = totalProtein;
        this.totalFat = totalFat;
    }
}
