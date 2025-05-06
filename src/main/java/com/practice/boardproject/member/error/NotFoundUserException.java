package com.practice.boardproject.member.error;

public class NotFoundUserException extends RuntimeException {
    private static final String ERROR_MESSAGE_FORMAT = "해당 사용자를 찾을 수 없습니다: %s";

    public NotFoundUserException(String username) {
        super(String.format(ERROR_MESSAGE_FORMAT, username));
    }
}