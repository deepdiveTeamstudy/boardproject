package com.practice.boardproject.security.jwt;

import com.practice.boardproject.global.exception.ErrorCode;
import com.practice.boardproject.global.exception.GlobalException;
import com.practice.boardproject.member.domain.Member;
import com.practice.boardproject.member.repository.MemberRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Member member = memberRepository.findById(Long.valueOf(username))
            .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        return new User(
            member.getId().toString(),
            member.getPassword(),
            Collections.emptyList()
        );
    }
}
