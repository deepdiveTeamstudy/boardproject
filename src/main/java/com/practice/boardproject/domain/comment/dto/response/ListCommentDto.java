package com.practice.boardproject.domain.comment.dto.response;

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
@Schema(description = "게시글 댓글 목록 조회 응답 객체")
public class ListCommentDto {

    @Schema(description = "댓글 ID")
    private long commentId;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "작성자 ID")
    private String username;

    @Schema(description = "작성일")
    private LocalDateTime createdAt;

}
