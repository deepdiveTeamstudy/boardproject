package com.practice.boardproject.global.security.jwt;

import com.practice.boardproject.global.exception.TokenException;
import com.practice.boardproject.member.domain.Member;
import com.practice.boardproject.member.dto.response.TokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분
    private final UserDetailsService userDetailsService;
    private final Key key; // java.security.Key로 import 할 것

    public TokenProvider(
            UserDetailsService userDetailsService,
            @Value("${jwt.secret}") String secretKey
    ) {
        // UserDetailsService 인스턴스를 클래스 필드에 할당.
        this.userDetailsService = userDetailsService;
        // 이후, 전달된 secretKey를 Base64 디코딩해서 JWT 서명에 사용할 Key 객체를 생성 및 초기화.
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 생성 메서드
    public TokenDTO generateTokenDTO(Member member) {
        // 현재 시간(msec)
        long now = System.currentTimeMillis();
        // 위에서 밀리초로 구해놓은 현재 시간에 토큰 만료 시간을 더해 유효 기간을 설정
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        // JWT 토큰 생성
        String accessToken = Jwts.builder()
                // 회원 아이디를 "sub"이라는 클레임으로 토큰에 추가
                .setSubject(String.valueOf(member.getUsername()))
                // 회원의 권한을 "auth"라는 클레임으로 토큰에 추가
//            .claim(AUTHORITIES_KEY, "ROLE_USER")
                // 만료 시간 설정
                .setExpiration(accessTokenExpiresIn)
                // 서명 및 알고리즘 설정
                .signWith(key)
                // 압축 = header + payload + signature
                .compact();
        return TokenDTO.builder()
                .grantType(BEARER_TYPE)
                .memberName(member.getUsername())
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .build();
    }

    // 토큰에 등록된 클레임의 sub에서 해당 회원의 아이디를 추출
    public String getUserId(String token) {
        return Jwts.parserBuilder()
                // 서명 키 설정
                .setSigningKey(key)
                // 파서 빌드
                .build()
                // JWT 토큰을 파싱하여 Claims Jws 객체로 변환
                .parseClaimsJws(token)
                // payload의 Claims 추출
                .getBody()
                // Claims 중에 등록 클레임에 해당하는 sub값 추출(회원 아이디)
                .getSubject();
    }

    // AccessToken으로 인증 객체 추출
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new TokenException("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            throw new TokenException("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            throw new TokenException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new TokenException("JWT 토큰이 잘못되었습니다.");
        }
    }

    // AccessToken에서 클레임을 추출하는 메서드
    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            /* 설명. 토큰이 만료되어 예외가 발생하더라도 클레임 값들은 뽑을 수 있다. */
            return e.getClaims();
        }
    }
}
