package com.practice.boardproject.member.controller;

import com.practice.boardproject.member.domain.MemberRequestDTO;
import com.practice.boardproject.member.domain.MemberResponseDTO;
import com.practice.boardproject.member.domain.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDTO> signUp(@RequestBody MemberRequestDTO requestDTO) {
        return ResponseEntity.ok(memberService.signUp(requestDTO));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<MemberResponseDTO> login(@RequestBody MemberRequestDTO requestDTO) {
        return ResponseEntity.ok(memberService.login(requestDTO));
    }
}
