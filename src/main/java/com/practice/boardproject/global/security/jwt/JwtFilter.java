package com.practice.boardproject.global.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/* 설명.
 *  이 JwtFilter는 커스텀 필터로, 클라이언트의 요청에 대해 JWT 토큰을 검사하여 유효성을 확인하고,
 *  유효한 경우 인증 정보를 설정하는 역할을 수행한다.
 *  OncePerRequestFilter를 상속받았기 때문에 클라이언트의 각 요청에 대해 딱 한 번만 실행된다.
 * */
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    /* 설명.
     *  - AUTHORIZATION_HEADER: 클라이언트가 서버에 요청할 때, 이 헤더에 JWT를 포함시켜 서버에 인증 정보를 전달
     *  - BEARER_PREFIX: JWT가 "Bearer" 타입임을 나타내는 접두사
     *  이 두 상수 필드는 요청에서 JWT 토큰을 올바르게 추출하고 처리할 수 있게 해주며 다음과 같이 조립될 것이다:
     *  ex) Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
     *  (https://datatracker.ietf.org/doc/html/rfc6750#section-6.1.1)
     * */
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer";
    private final TokenProvider tokenProvider;

    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    // 각 요청에 대해 JWT 토큰을 검사하고 유효한 경우 SecurityContext에 인증 정보를 설정
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // 요청에서 토큰값 추출
        String jwt = resolveToken(request);

        // 추출한 토큰의 유효성 검사 후 인증을 위해 Authentication 객체를 SecurityContextHolder에 담는다. 스레드 로컬에 저장
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    // Request Header에서 JWT 토큰을 추출(위에 정의한 static final 변수 두개 사용)
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
