package com.practice.boardproject.member.service;

import com.practice.boardproject.member.dto.request.LoginRequest;
import com.practice.boardproject.member.dto.request.SignUpRequest;
import com.practice.boardproject.member.dto.response.SignUpResponse;
import com.practice.boardproject.member.dto.response.TokenDTO;

public interface MemberAuthService {

    SignUpResponse signUp(SignUpRequest signUpRequest);

    TokenDTO login(LoginRequest loginRequest);
}
