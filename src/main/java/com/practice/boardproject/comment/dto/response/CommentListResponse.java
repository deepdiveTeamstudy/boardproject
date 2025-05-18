package com.practice.boardproject.comment.dto.response;

import com.practice.boardproject.comment.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;


public record CommentListResponse(
        List<CommentDetail> comments,
        int size
) {

    public static CommentListResponse from(List<Comment> comments) {
        List<CommentDetail> commentDetails = comments.stream()
                .map(CommentDetail::from)
                .toList();

        return new CommentListResponse(commentDetails, commentDetails.size());
    }

    public record CommentDetail(
            Long id,
            String content,
            String author,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        public static CommentDetail from(Comment comment) {
            return new CommentDetail(
                    comment.getId(),
                    comment.getContent(),
                    comment.getAuthor().getUsername(),
                    comment.getCreatedAt(),
                    comment.getUpdatedAt()
            );
        }
    }
}