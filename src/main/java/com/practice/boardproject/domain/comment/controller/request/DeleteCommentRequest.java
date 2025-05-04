package com.practice.boardproject.domain.comment.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "댓글 삭제 요청 객체")
public record DeleteCommentRequest(

  @NotBlank
  @Schema(description = "작성자 비밀번호")
  String password

) {

}
