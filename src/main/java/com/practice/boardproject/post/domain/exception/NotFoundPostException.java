package com.practice.boardproject.post.domain.exception;

public class NotFoundPostException extends RuntimeException {
    private static final String ERROR_MESSAGE_FORMAT = "해당 게시글을 찾을 수 없습니다: %s";

    public NotFoundPostException(Long postId) {
        super(String.format(ERROR_MESSAGE_FORMAT, postId));
    }
}
