package com.practice.boardproject.post.domain.dto;

import java.time.LocalDateTime;

public record PostCreateResponse(
        String title,
        String content,
        String author,
        LocalDateTime createdAt
) {
}
