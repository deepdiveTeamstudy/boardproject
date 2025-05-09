package com.practice.boardproject.member.controller;

import com.practice.boardproject.member.domain.Member;
import com.practice.boardproject.member.dto.response.TokenDTO;
import com.practice.boardproject.member.repository.MemberRepository;
import com.practice.boardproject.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @GetMapping("/signUp")
    public ResponseEntity<Void> signUp(
        @RequestParam String username,
        @RequestParam String password
    ) {
        memberRepository.save(
            Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public ResponseEntity<TokenDTO> login(
        @RequestParam String username
    ) {
        return ResponseEntity.ok(tokenProvider.generateTokenDTO(memberRepository.findByUsername(username).get()));
    }

    @GetMapping("/auth")
    public ResponseEntity<String> auth(
    ) {
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
