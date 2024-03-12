package com.d101.back.entity;

import com.d101.back.entity.enums.Provider;
import com.d101.back.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Setter
@Entity
public class User extends BaseTimeEntity {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;

    private String image;

    private int calorie;
    private int height;
    private int weight;
    private String gender;
    private String refresh_token;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String oauthId;


    @OneToMany(mappedBy = "user")
    private List<Meal> meals;

    @Builder
    public User(String email, String username,String image,  Role role, Provider provider, String oauthId) {
        this.email = email;
        this.username = username;
        this.image = image;
        this.role = role;
        this.provider = provider;
        this.oauthId = oauthId;
    }

}
