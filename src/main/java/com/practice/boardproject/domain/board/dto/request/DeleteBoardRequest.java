package com.practice.boardproject.domain.board.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "게시글 삭제 요청 객체")
public record DeleteBoardRequest(

  @NotBlank
  @Schema(description = "작성자 비밀번호")
  String password

) {

}
