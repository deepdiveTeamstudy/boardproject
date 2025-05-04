package com.practice.boardproject.domain.comment.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "댓글 수정 요청 객체")
public record UpdateCommentRequest(

  @NotBlank
  @Schema(description = "작성자 비밀번호")
  String password,

  @NotBlank
  @Schema(description = "댓글 내용")
  String content

) {

}
