package com.practice.boardproject.domain.member.service;

import com.practice.boardproject.domain.member.Member;
import com.practice.boardproject.domain.member.dto.request.CreateMemberRequest;
import com.practice.boardproject.domain.member.repository.MemberRepository;
import com.practice.boardproject.global.api_response.exception.GlobalException;
import com.practice.boardproject.global.api_response.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommandMemberServiceImpl implements CommandMemberService {

  private final MemberRepository memberRepository;

  public void createMember(CreateMemberRequest request) {
    if (memberRepository.existsMemberByUsername(request.username())) {
      throw new GlobalException(ErrorStatus.BAD_REQUEST, "이미 사용 중인 ID입니다.");
    }

    memberRepository.save(
      Member.builder()
        .username(request.username())
        .password(request.password())
        .build()
    );
  }
}
