package com.practice.boardproject.member.controller;

import com.practice.boardproject.member.dto.request.LoginRequest;
import com.practice.boardproject.member.dto.response.TokenDTO;
import com.practice.boardproject.member.service.MemberAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MemberAuthService memberAuthService;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginRequest request) {
        TokenDTO token = memberAuthService.login(request);
        return ResponseEntity.ok(token);
    }
}
