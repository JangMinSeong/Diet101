package com.d101.back.dto.oauth;

import com.d101.back.entity.enums.Provider;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoLoginReq implements OAuthLoginReq {

    private String authorizationCode;

    @Override
    public Provider getProviderName() {
        return Provider.KAKAO;
    }
}
