package com.d101.back.filter;

import com.d101.back.exception.UnAuthorizedException;
import com.d101.back.exception.response.ExceptionStatus;
import com.d101.back.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
//    private final RedisTemplate redisTemplate;

    @Value("${auth.white-list}")
    private String[] whiteList;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (auth == null) {
            AntPathMatcher antPathMatcher = new AntPathMatcher();
            for (String list : whiteList) {
                if (antPathMatcher.match(list, path)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }

        try {
            // Request Header 에서 JWT 토큰 추출
            String token = parseBearerToken(auth);

            if (path.equals("/members/reissue")) {
                filterChain.doFilter(request, response);
                return;
            }

            //토큰 유효성 검사
            if (jwtTokenProvider.validateAndGetClaims(token) != null) {
//                if (redisTemplate.opsForValue().get(token) != null) {
//                    throw new TokenException(BLACK_TOKEN);
//                }
                // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new UnAuthorizedException(ExceptionStatus.JWT_TOKEN_INVALID);
        }
    }

    private String parseBearerToken(String auth) {
        return Optional.of(auth)
                .filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer "))
                .map(token -> token.substring(7))
                .orElseThrow(() -> new UnAuthorizedException(ExceptionStatus.JWT_TOKEN_INVALID));
    }

}