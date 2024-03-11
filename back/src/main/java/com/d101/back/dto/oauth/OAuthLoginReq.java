package com.d101.back.dto.oauth;


import com.d101.back.entity.enums.Provider;

public interface OAuthLoginReq {

    default Provider getProviderName() {
        return null;
    }

    default String getAuthorizationCode() {
        return null;
    }

    default String getState() {
        return null;
    }
}
