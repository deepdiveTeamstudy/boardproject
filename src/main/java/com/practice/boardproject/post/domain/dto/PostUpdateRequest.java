package com.practice.boardproject.post.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record PostUpdateRequest(
        @NotBlank
        Long postId,

        @NotBlank
        String title,

        @NotBlank
        String content
) {
}
