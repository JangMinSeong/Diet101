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
import com.d101.back.exception.BadRequestException;
import com.d101.back.exception.NoSuchDataException;
import com.d101.back.exception.UnAuthorizedException;
import com.d101.back.exception.response.ExceptionStatus;
import com.d101.back.repository.AllergyRepository;
import com.d101.back.repository.UserRepository;
import com.d101.back.util.JwtTokenProvider;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
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
            user.setRefresh_token(loginTokenDto.getRefreshToken()); // ⭐ 리프레시토큰 저장
            userRepository.save(user);
            return loginTokenDto;
        } catch (Exception e) {
            throw new UnAuthorizedException(ExceptionStatus.OAUTH_LOGIN_FAIL);
        }
    }

    public User createNewUser(OAuth2UserInfo oAuth2UserInfo) {
        User user = User.builder()
                .email(oAuth2UserInfo.getEmail())
                .username(oAuth2UserInfo.getNickname())
                .image(oAuth2UserInfo.getImageUrl())
                .role(Role.ROLE_USER)
                .oauthId(oAuth2UserInfo.getId())
                .provider(oAuth2UserInfo.getProvider())
                .build();
        return user;
    }

    @Transactional
    public LoginTokenDto reissue(LoginTokenDto loginTokenDto) {
        String accessToken = loginTokenDto.getAccessToken();
        String refreshToken = loginTokenDto.getRefreshToken();
        HashMap<Object, String> claims = jwtProvider.parseClaimsByExpiredToken(accessToken); //만료된 atk를 검증하고 claim정보를 가져옴
        // atk가 만료되지 않은 상황은 재발급하지 않음
        if(claims == null){
            throw new BadRequestException(ExceptionStatus.JWT_TOKEN_ALIVE);
        }
        Claims refreshClaims = jwtProvider.validateAndGetClaims(refreshToken);
        String userName = claims.get("sub");
        User user = userRepository.findByEmail(userName).orElseThrow(()->new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND));
        if (!Objects.equals(user.getRefresh_token(), refreshToken)){  // atk의 userName으로 db에 저장된 rtk와 전달받은 rtk를 비교
            throw new BadRequestException(ExceptionStatus.REFRESH_TOKEN_INVALID);
        }

        Date exp = refreshClaims.getExpiration();
        Date current = Date.from(OffsetDateTime.now().toInstant());

        //만료 시간과 현재 시간의 간격 계산
        //만일 1일 미만인 경우에는 Refresh Token도 다시 생성
        long gapTime = (exp.getTime() - current.getTime());
        if(gapTime < (1000 * 60 * 60 * 24  ) ){
            log.info("new Refresh Token required...  ");
            refreshToken = jwtProvider.generateRefreshToken(user.getEmail());
            user.setRefresh_token(refreshToken);
        }
        accessToken = jwtProvider.generateAccessToken(user.getEmail(),user.getRole().name());
        return new LoginTokenDto(user.getEmail(),accessToken,refreshToken);
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

    public List<String> getAllergy(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchDataException(ExceptionStatus.USER_NOT_FOUND));
        QAllergy allergy = QAllergy.allergy;
        List<AllergyType> allergies = jpaQueryFactory
                .select(allergy.key.allergy)
                .from(allergy)
                .where(allergy.key.user_id.eq(user.getId()))
                .fetch();
        return  allergies.stream().map(AllergyType::name).toList();
    }

}
