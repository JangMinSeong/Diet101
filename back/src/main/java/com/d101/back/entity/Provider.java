package com.d101.back.entity;

public enum Provider {
    KAKAO, NAVER, GOOGLE;
    public String getSocialName(){
        return this.name().toLowerCase();
    }
}
