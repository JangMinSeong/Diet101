package com.d101.back.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyUserReq {

    private String username;
    private String gender;
    private int calorie;
    private int height;
    private int weight;

}
