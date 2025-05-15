package com.practice.boardproject.global.security;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

import com.practice.boardproject.global.security.jwt.JwtAccessDeniedHandler;
import com.practice.boardproject.global.security.jwt.JwtAuthenticationEntryPoint;
import com.practice.boardproject.global.security.jwt.JwtFilter;
import com.practice.boardproject.global.security.jwt.TokenProvider;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    // JWT 토큰을 발급하고 검증하는 Token Provider
    private final TokenProvider tokenProvider;
    // 인증 실패 관련 예외
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    // 접근 거부 관련 예외
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // 암호화 처리를 위한 PasswordEncoder 객체
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 아이디와 패스워드의 일치 여부를 확인할 때 사용하는 객체
    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 필터 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            // CSRF 보호 비활성화
            .csrf(AbstractHttpConfigurer::disable)
            // 예외 처리
            .exceptionHandling(exception -> {
                // 필요한 권한이 없을 때 403(Forbidden)을 반환
                exception.authenticationEntryPoint(jwtAuthenticationEntryPoint);
                // 인증되지 않은 접근 시 401(Unauthorized)를 반환
                exception.accessDeniedHandler(jwtAccessDeniedHandler);
            })
            // HTTP 요청에 대한 접근 권한 설정
            .authorizeHttpRequests(auth -> {
                // CORS Preflight 요청 허용
                auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                // 특정 경로는 무조건 허용
                auth.requestMatchers("/signup", "/login").permitAll();
                // Swagger API 문서 허용
                auth.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll();
                auth.anyRequest().authenticated();
            })
            // 세션 방식을 사용하지 않음
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            // 기본 CORS 설정 사용
            .cors(cors -> {})
            // 우리가 직접 작성한 커스텀 필터인 JwtFilter를 필터 체인에 추가
            .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
