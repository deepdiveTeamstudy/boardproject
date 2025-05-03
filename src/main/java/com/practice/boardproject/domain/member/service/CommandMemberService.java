package com.practice.boardproject.domain.member.service;

import com.practice.boardproject.domain.member.dto.request.CreateMemberRequest;

public interface CommandMemberService {

  void createMember(CreateMemberRequest request);
}
