package com.d101.back.api;

import com.d101.back.dto.oauth.*;
import com.d101.back.exception.UnAuthorizedException;
import com.d101.back.exception.response.ExceptionStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;


@Slf4j
public class KakaoOAuthApi extends OAuthApi {

    public KakaoOAuthApi(ClientRegistration provider, OAuthLoginReq oAuthLoginReq) {
        super(provider, oAuthLoginReq);
    }

    @Override
    public OAuth2UserInfo makeUserInfo(Map<String, Object> param) {
        return new OAuth2Info.KakaoOAuth2UserInfo(param);
    }

    @Override
    public OAuthTokenRes getOAuthToken(ClientRegistration provider, OAuthLoginReq loginReq) {
        WebClient webClient = WebClient.builder()
                .baseUrl(provider.getProviderDetails().getTokenUri())
                .defaultHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
        OAuthTokenRes response = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("grant_type", provider.getAuthorizationGrantType().getValue())
                        .queryParam("client_id", provider.getClientId())
                        .queryParam("redirect_uri", provider.getRedirectUri())
                        .queryParam("code", loginReq.getAuthorizationCode())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(KakaoTokenRes.class)
                .block();
        if (response != null) {
            return response;
        } else {
            throw new UnAuthorizedException(ExceptionStatus.UNAUTHORIZED);
        }
    }
}