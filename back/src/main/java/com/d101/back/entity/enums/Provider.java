package com.d101.back.entity.enums;

public enum Provider {
    KAKAO, NAVER, GOOGLE;
    public String getSocialName(){
        return this.name().toLowerCase();
    }
}
