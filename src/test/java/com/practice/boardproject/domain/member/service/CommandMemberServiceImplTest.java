package com.practice.boardproject.domain.member.service;

import static org.junit.jupiter.api.Assertions.*;

import com.practice.boardproject.domain.member.dto.request.CreateMemberRequest;
import com.practice.boardproject.global.api_response.exception.GlobalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CommandMemberServiceImplTest {

  @Autowired
  CommandMemberService commandMemberService;

  @Test
  @DisplayName("이미 존재하는 username으로 회원가입 할 수 없다.")
  @Rollback
  void 회원가입_중복ID_검증() {

    CreateMemberRequest request1 = new CreateMemberRequest("name1", "pw");
    commandMemberService.createMember(request1);

    CreateMemberRequest request2 = new CreateMemberRequest("name2", "pw");
    commandMemberService.createMember(request2);

    CreateMemberRequest request3 = new CreateMemberRequest("name1", "pw");
    assertThrows(GlobalException.class, () -> commandMemberService.createMember(request3));
  }

}