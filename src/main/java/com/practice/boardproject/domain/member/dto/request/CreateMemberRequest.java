package com.practice.boardproject.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "회원가입 요청 객체")
public record CreateMemberRequest(

  @NotBlank
  @Schema(description = "로그인 ID")
  String username,

  @NotBlank
  @Schema(description = "비밀번호")
  String password


) {

}
