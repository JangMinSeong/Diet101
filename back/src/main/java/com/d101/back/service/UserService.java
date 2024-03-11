package com.d101.back.service;

import com.d101.back.api.KakaoOAuthApi;
import com.d101.back.api.OAuthApi;
import com.d101.back.dto.LoginTokenDto;
import com.d101.back.dto.oauth.OAuth2UserInfo;
import com.d101.back.dto.oauth.OAuthLoginReq;
import com.d101.back.dto.request.ModifyUserReq;
import com.d101.back.entity.Allergy;
import com.d101.back.entity.CustomUserDetails;
import com.d101.back.entity.QAllergy;
import com.d101.back.entity.User;
import com.d101.back.entity.composite.UserAllergyKey;
import com.d101.back.entity.enums.AllergyType;
import com.d101.back.entity.enums.Role;
import com.d101.back.exception.NoSuchDataException;
import com.d101.back.exception.UnAuthorizedException;
import com.d101.back.exception.response.ExceptionStatus;
import com.d101.back.repository.AllergyRepository;
import com.d101.back.repository.UserRepository;
import com.d101.back.util.JwtTokenProvider;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final InMemoryClientRegistrationRepository inMemoryClient;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtProvider;
    private final AllergyRepository allergyRepository;
    private final JPAQueryFactory jpaQueryFactory;

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

    @Transactional
    public User createNewUser(OAuth2UserInfo oAuth2UserInfo) {
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

    @Transactional
    public void updateUserInfo(String email, ModifyUserReq req) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND));
        user.setUsername(req.getUsername());
        user.setGender(req.getGender());
        user.setCalorie(req.getCalorie());
        user.setHeight(req.getHeight());
        user.setWeight(req.getWeight());
    }

    @Transactional
    public void updateAllergy(String email, List<String> allergies) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND));
        // 원래 있던 알러지 정보 삭제
        QAllergy allergy = QAllergy.allergy;
        jpaQueryFactory
                .delete(allergy)
                .where(allergy.key.user_id.eq(user.getId()))
                .execute();
        // 새로운 알러지 정보 추가
        List<Allergy> allergiesToSave = allergies.stream()
                .map(arg -> new Allergy(new UserAllergyKey(user.getId(), AllergyType.valueOf(arg))))
                .toList();
        allergyRepository.saveAll(allergiesToSave);
    }

}
