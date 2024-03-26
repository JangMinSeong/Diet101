package com.d101.back.entity;

import com.d101.back.dto.request.LoginReq;
import com.d101.back.entity.enums.Provider;
import com.d101.back.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
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
    private int age;
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

    public static User of(LoginReq req) {
        User user = new User();
        user.setEmail(req.getEmail());
        user.setUsername(req.getUsername());
        user.setGender(req.getGender());
        user.setAge(req.getAge());
        user.setImage(req.getImage());
        user.setOauthId(req.getOauthId());
        user.setRole(Role.ROLE_USER);
        user.setProvider(Provider.KAKAO);
        return user;
    }

}
