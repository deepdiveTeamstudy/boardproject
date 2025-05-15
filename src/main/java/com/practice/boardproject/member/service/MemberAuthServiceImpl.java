package com.practice.boardproject.member.service;

import com.practice.boardproject.global.exception.ErrorCode;
import com.practice.boardproject.global.exception.GlobalException;
import com.practice.boardproject.member.domain.Member;
import com.practice.boardproject.member.dto.request.LoginRequest;
import com.practice.boardproject.member.dto.request.SignUpRequest;
import com.practice.boardproject.member.dto.response.SignUpResponse;
import com.practice.boardproject.member.dto.response.TokenDTO;
import com.practice.boardproject.member.repository.MemberRepository;
import com.practice.boardproject.member.security.CustomUserDetails;
import com.practice.boardproject.global.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberAuthServiceImpl implements MemberAuthService {

    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Override
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        if (memberRepository.existsByUsername(signUpRequest.username())) {
            throw new GlobalException(ErrorCode.DUPLICATE_USER);
        }

        Member member = Member.builder()
                .username(signUpRequest.username())
                .password(passwordEncoder.encode(signUpRequest.password()))
                .build();

        memberRepository.save(member);
        return new SignUpResponse(member.getPassword());
    }

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