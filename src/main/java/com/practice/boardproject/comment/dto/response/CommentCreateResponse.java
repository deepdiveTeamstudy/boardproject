package com.practice.boardproject.comment.dto.response;

import java.time.LocalDateTime;

public record CommentCreateResponse(
        String content,
        String author,
        LocalDateTime createdAt
) {
}
