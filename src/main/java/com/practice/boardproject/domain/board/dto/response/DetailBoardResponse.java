package com.practice.boardproject.domain.board.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "게시글 상세 조회 응답 객체")
public class DetailBoardResponse {

    @Schema(description = "게시글 제목")
    private String title;

    @Schema(description = "게시글 내용")
    private String content;

    @Schema(description = "작성자 ID")
    private String author;

    @Schema(description = "작성일")
    private LocalDateTime createdAt;

}
