package com.practice.boardproject.post.domain.dto.response;

import java.time.LocalDateTime;

public record PostCreateResponse(
        String title,
        String content,
        String author,
        LocalDateTime createdAt
) {
}
