package com.practice.boardproject.global.exception;

public record ErrorResponse(
        int status,
        String code,
        String message
) {
}
