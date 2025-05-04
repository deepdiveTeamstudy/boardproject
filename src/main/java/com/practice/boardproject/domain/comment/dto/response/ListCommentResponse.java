package com.practice.boardproject.domain.comment.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "게시글 댓글 목록 조회 응답 객체")
public class ListCommentResponse {

    @Default
    @Schema(description = "댓글 목록")
    private List<ListCommentDto> commentList = new ArrayList<>();
}
