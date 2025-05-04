package com.practice.boardproject.domain.board.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "게시글 목록 조회 응답 DTO")
public class ListBoardDto {

  @Schema(description = "게시글 ID")
  private long boardId;

  @Schema(description = "제목")
  private String title;

  @Schema(description = "작성자 ID")
  private String username;

  @Schema(description = "작성일")
  private LocalDateTime createdAt;

}