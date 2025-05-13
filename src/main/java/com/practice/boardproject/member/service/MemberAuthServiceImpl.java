package com.practice.boardproject.member.service;

import com.practice.boardproject.member.domain.Member;
import com.practice.boardproject.member.dto.request.LoginRequest;
import com.practice.boardproject.member.dto.response.TokenDTO;
import com.practice.boardproject.member.security.CustomUserDetails;
import com.practice.boardproject.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberAuthServiceImpl implements MemberAuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Override
    public TokenDTO login(LoginRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        CustomUserDetails customUserDetails = (CustomUserDetails) authenticate.getPrincipal();
        Member principal = customUserDetails.member();
        return tokenProvider.generateTokenDTO(principal);
    }
}
