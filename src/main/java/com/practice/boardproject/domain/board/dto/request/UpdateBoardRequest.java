package com.practice.boardproject.domain.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "게시글 수정 요청 객체")
public record UpdateBoardRequest(

  @NotBlank
  @Schema(description = "작성자 비밀번호")
  String password,

  @NotBlank
  @Schema(description = "게시글 제목")
  String title,

  @NotBlank
  @Schema(description = "게시글 내용")
  String content
) {

}
