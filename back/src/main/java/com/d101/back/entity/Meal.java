package com.d101.back.entity;

import java.util.List;

import com.d101.back.entity.enums.Dunchfast;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
    @OneToMany(mappedBy = "meal")
    private List<Intake> intakes;

    @Builder
    public Meal(String image, Dunchfast type, int totalCalorie, double totalCarbohydrate, double totalProtein, double totalFat)  {
        this.image = image;
        this.type = type;
        this.totalCalorie = totalCalorie;
        this.totalCarbohydrate = totalCarbohydrate;
        this.totalProtein = totalProtein;
        this.totalFat = totalFat;
    }
}
