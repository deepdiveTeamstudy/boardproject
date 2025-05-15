package com.practice.boardproject.comment.dto.response;

import java.time.LocalDateTime;

public record CommentCreateResponse(
        String comment,
        String author,
        LocalDateTime createdAt
) {
}
