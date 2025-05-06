package com.practice.boardproject.member.domain;

import org.springframework.stereotype.Service;

@Service
public class MemberService {

    // 생성자 주입
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     * */
    public MemberResponseDTO signUp(MemberRequestDTO requestDTO) {
        Member newMember = Member.builder()
                .username(requestDTO.getUsername())
                .password(requestDTO.getPassword()) // 실제 서비스에서는 암호화가 필요하다.
                .build();

        Member saved = memberRepository.save(newMember);

        return MemberResponseDTO.builder()
                .username(saved.getUsername())
                .build();
    }

    /**
     * 로그인
     * */
    public MemberResponseDTO login(MemberRequestDTO requestDTO) {
        Member member = memberRepository.findByUsername(requestDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!member.getPassword().equals(requestDTO.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return MemberResponseDTO.builder()
                .username(member.getUsername())
                .build();
    }

}
