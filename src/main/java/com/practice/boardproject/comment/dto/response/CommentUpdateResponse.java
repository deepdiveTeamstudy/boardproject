package com.practice.boardproject.comment.dto.response;

import java.time.LocalDateTime;

public record CommentUpdateResponse(
        String content,
        String author,
        LocalDateTime updatedAt
) {
}
