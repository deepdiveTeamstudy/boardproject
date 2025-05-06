package com.practice.boardproject.global.exception;

import com.practice.boardproject.post.domain.exception.NotFoundPostException;
import com.practice.boardproject.post.domain.exception.NotFoundUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotFoundUserException.class, NotFoundPostException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundUserException e) {
        return createErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다");
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(HttpStatus status, String message) {
        ErrorResponse response = new ErrorResponse(status.value(), message);
        return ResponseEntity.status(status).body(response);
    }

    public record ErrorResponse(int status, String message) {
    }
}