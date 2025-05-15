package com.practice.boardproject.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentCreateRequest(

        @NotBlank
        String content,

        @NotNull
        Long postId
) {
}
