package com.d101.back.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModifyUserReq {
    private int activity;
    private int calorie;
    private int height;
    private int weight;

}
