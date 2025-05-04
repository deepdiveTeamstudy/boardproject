package com.practice.boardproject.domain.comment.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "댓글 생성 요청 객체")
public record CreateCommentRequest(

  @NotBlank
  @Schema(description = "작성자 ID")
  String username,

  @NotBlank
  @Schema(description = "댓글 내용")
  String content
) {

}
