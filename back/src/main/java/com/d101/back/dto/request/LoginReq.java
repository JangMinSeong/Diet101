package com.d101.back.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq {

    private String email;
    private String username;
    private String image;
    private String gender; // male, female
    private int age;
    private String oauthId;

}
