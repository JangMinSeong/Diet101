package com.d101.back.service;

import com.d101.back.api.KakaoOAuthApi;
import com.d101.back.api.OAuthApi;
import com.d101.back.dto.LoginTokenDto;
import com.d101.back.dto.oauth.OAuth2UserInfo;
import com.d101.back.dto.oauth.OAuthLoginReq;
import com.d101.back.entity.CustomUserDetails;
import com.d101.back.entity.Role;
import com.d101.back.entity.User;
import com.d101.back.exception.NoSuchDataException;
import com.d101.back.exception.UnAuthorizedException;
import com.d101.back.exception.response.ExceptionStatus;
import com.d101.back.repository.UserRepository;
import com.d101.back.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final InMemoryClientRegistrationRepository inMemoryClient;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtProvider;

    public CustomUserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email).map(CustomUserDetails::fromEntity)
                .orElseThrow(()-> new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND));
    }

    public LoginTokenDto loginOauth(OAuthLoginReq loginReq) {
        try {
            ClientRegistration provider = inMemoryClient.findByRegistrationId(
                    loginReq.getProviderName().getSocialName()); //provider 찾음
            OAuthApi oAuthApi= new KakaoOAuthApi(provider, loginReq);

            OAuth2UserInfo oAuth2UserInfo = oAuthApi.loginProcess();
            User user = userRepository.findByEmail(oAuth2UserInfo.getEmail())
                    .orElseGet(() -> createNewUser(oAuth2UserInfo));

            LoginTokenDto loginTokenDto = jwtProvider.getLoginResponse(user);
//            redisTemplate.opsForValue()
//                    .set("RTK:"+loginTokenDTO.getUserId(),loginTokenDTO.getRefreshToken(),
//                            Duration.ofDays(jwtProvider.getRefreshTokenValidityTime()));
            return loginTokenDto;
        } catch (Exception e) {
            throw new UnAuthorizedException(ExceptionStatus.OAUTH_LOGIN_FAIL);
        }
    }

    private User createNewUser(OAuth2UserInfo oAuth2UserInfo) {
        User user = User.builder()
                .email(oAuth2UserInfo.getEmail())
                .username(oAuth2UserInfo.getNickname())
                .image(oAuth2UserInfo.getImageUrl())
                .role(Role.ROLE_USER)
                .oauthId(oAuth2UserInfo.getId())
                .provider(oAuth2UserInfo.getProvider())
                .build();
        return userRepository.save(user);
    }
}
