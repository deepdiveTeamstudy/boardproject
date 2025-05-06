package com.practice.boardproject.post.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record PostCreateRequest(
        @NotBlank
        String username,

        @NotBlank
        String title,

        @NotBlank
        String content
) {
}
