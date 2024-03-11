package com.d101.back.entity;

import com.d101.back.entity.enums.Provider;
import com.d101.back.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;

    private String image;

    private int calorie;
    private int height;
    private int weight;

    private String gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String oauthId;

    @Builder
    public User(String email, String username,String image,  Role role, Provider provider, String oauthId, int calorie, int height, int weight, String gender) {
        this.email = email;
        this.username = username;
        this.image = image;
        this.role = role;
        this.provider = provider;
        this.oauthId = oauthId;
        this.calorie = calorie;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
    }

}
