package com.d101.back.dto.oauth;


import com.d101.back.entity.enums.Provider;

import java.util.Map;

public class OAuth2Info {

    public static class KakaoOAuth2UserInfo extends OAuth2UserInfo {

        public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
            super(attributes, Provider.KAKAO);
        }

        @Override
        public String getEmail() {
            Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

            if (account == null) {
                return null;
            }

            return (String) account.get("email");
        }

        @Override
        public String getId() {
            return attributes.get("id").toString();
        }

        @Override
        public String getNickname() {
            Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

            if (account == null) {
                return null;
            }

            Map<String, Object> profile = (Map<String, Object>) account.get("profile");

            if (profile == null) {
                return null;
            }

            return (String) profile.get("nickname");
        }

        @Override
        public String getImageUrl() {
            Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

            if (account == null) {
                return null;
            }

            Map<String, Object> profile = (Map<String, Object>) account.get("profile");

            if (profile == null) {
                return null;
            }

            return (String) profile.get("thumbnail_image_url");
        }
    }


}
