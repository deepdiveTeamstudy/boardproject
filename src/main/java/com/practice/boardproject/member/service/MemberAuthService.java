package com.practice.boardproject.member.service;

import com.practice.boardproject.member.dto.request.LoginRequest;
import com.practice.boardproject.member.dto.response.TokenDTO;

public interface MemberAuthService {
    TokenDTO login(LoginRequest loginRequest);
}
