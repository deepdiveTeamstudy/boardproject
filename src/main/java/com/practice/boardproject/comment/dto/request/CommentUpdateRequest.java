package com.practice.boardproject.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentUpdateRequest(

        @NotNull
        Long commentId,

        @NotBlank
        String content
) {
}
