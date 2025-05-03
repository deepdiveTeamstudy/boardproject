package com.practice.boardproject.domain.board.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;

@Schema(description = "게시글 목록 검색 요청 객체")
public record SearchBoardRequest(

    @Schema(description = "검색 키워드(게시글 제목, 게시글 내용)")
    String searchKeyword,

    @Min(1)
    @Schema(description = "페이지 번호", requiredMode = RequiredMode.REQUIRED)
    int page,

    @Min(1)
    @Schema(description = "페이지별 개수", requiredMode = RequiredMode.REQUIRED)
    int size
) {

    public PageRequest pageRequest() {
        return PageRequest.of(page - 1, size);
    }

}