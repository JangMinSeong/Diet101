package com.d101.back.dto.oauth;

import com.d101.back.entity.Provider;
import lombok.Getter;

import java.util.Map;

public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;
    @Getter
    protected Provider provider;
    public OAuth2UserInfo(Map<String, Object> attributes,Provider provider) {
        this.attributes = attributes;
        this.provider = provider;
    }

    public abstract String getEmail();

    public abstract String getId(); //소셜 식별 값 : 구글 - "sub", 카카오 - "id", 네이버 - "id"
    public abstract String getNickname();
    public abstract String getImageUrl();
}