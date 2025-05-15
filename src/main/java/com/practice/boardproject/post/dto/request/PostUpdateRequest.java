package com.practice.boardproject.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostUpdateRequest(
        @NotNull
        Long postId,

        @NotBlank
        String title,

        @NotBlank
        String content
) {
}
