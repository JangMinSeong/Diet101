package com.d101.back.entity;

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

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String oauthId;

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
