package com.d101.back.util;

import com.d101.back.dto.LoginTokenDto;
import com.d101.back.entity.User;
import com.d101.back.exception.UnAuthorizedException;
import com.d101.back.exception.response.ExceptionStatus;
import com.d101.back.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String key;
    @Value("${jwt.token.access-expiration-time}")
    private long accessExpirationTime;
    @Value("${jwt.token.refresh-expiration-time}")
    private long refreshExpirationTime;

    public long getRefreshTokenValidityTime() {
        return refreshExpirationTime;
    }
    public LoginTokenDto getLoginResponse(User user){
        return new LoginTokenDto(user.getEmail(), generateAccessToken(user.getId(), user.getEmail(), user.getRole().name())
                ,generateRefreshToken(user.getId(), user.getEmail()));
    }
    public LoginTokenDto getLoginResponse(Long id, String email, Authentication authentication){
        return new LoginTokenDto(email, generateAccessToken(id, email,getAuthorities(authentication)),generateRefreshToken(id, email));
    }

    public String getAuthorities(Authentication authentication){
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    //사용자 정보를 기반으로 토큰을 생성하여 반환 해주는 메서드
    public String generateAccessToken(Long id, String email,String authorities) {
        return Jwts.builder()
                .signWith(createKey())   // 서명
                .setSubject(email)  // JWT 토큰 제목
                .setId(String.valueOf(id))
                .claim("auth",authorities)  //권한정보 저장
                .setExpiration(Date.from(ZonedDateTime.now().plusDays(accessExpirationTime).toInstant()))    // JWT 토큰 만료 시간
                .compact();
    }
    public String generateRefreshToken(Long id,String email) {
        return Jwts.builder()
                .signWith(createKey())   // 서명
                .setSubject(email)  // JWT 토큰 제목
                .setId(String.valueOf(id))
                .setExpiration(Date.from(ZonedDateTime.now().plusDays(refreshExpirationTime).toInstant()))    // JWT 토큰 만료 시간
                .compact();
    }
    // HS512 알고리즘을 사용해 서명
    private Key createKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    //토큰을 기반으로 사용자 정보를 반환 해주는 메서드
    public String parseTokenToUserInfo(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(createKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    //토큰의 유효성을 체크하고 Claims 정보를 반환받는 메서드
    public Claims validateAndGetClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(createKey()).build().parseClaimsJws(token).getBody();
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            throw new UnAuthorizedException(ExceptionStatus.JWT_TOKEN_INVALID);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            throw new UnAuthorizedException(ExceptionStatus.JWT_TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            throw new UnAuthorizedException(ExceptionStatus.UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
            throw new UnAuthorizedException(ExceptionStatus.JWT_CLAIMS_STRING_IS_EMPTY);
        }
    }
    // 토큰의 정보로 Authentication 가져옴
    public Authentication getAuthentication(String accessToken, UserService userService) {
        Claims claims = validateAndGetClaims(accessToken);
        UserDetails principal = userService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(principal, accessToken, principal.getAuthorities());
    }
    // 만료되었지만 올바른 형식의 Token인지 검사.
    public HashMap<Object,String> parseClaimsByExpiredToken(String accessToken){
        try {
            Jwts.parserBuilder().setSigningKey(createKey()).build().parseClaimsJws(accessToken);
        } catch (ExpiredJwtException e) { // 토큰 검사 과정에서 토큰의 유효시간을 가장 나중에 체크하기 때문에 이전 예외에 걸리지않으면 올바른 토큰임.
            try {
                String[] splitJwt = accessToken.split("\\.");
                Base64.Decoder decoder = Base64.getDecoder();
                String payload = new String(decoder.decode(splitJwt[1].getBytes()));

                return new ObjectMapper().readValue(payload, HashMap.class);
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

}