package com.practice.boardproject.global.exception;

import java.net.BindException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(GlobalException e) {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(new ErrorResponse(
                        e.getErrorCode().getStatus().value(),
                        e.getErrorCode().getCode(),
                        e.getErrorCode().getMessage())
                );
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ErrorResponse> handleValidationException() {
        return ResponseEntity
                .status(ErrorCode.INVALID_INPUT.getStatus())
                .body(new ErrorResponse(
                        ErrorCode.INVALID_INPUT.getStatus().value(),
                        ErrorCode.INVALID_INPUT.getCode(),
                        ErrorCode.INVALID_INPUT.getMessage())
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException() {
        return ResponseEntity
                .status(ErrorCode.NOT_MATCH_PASSWORD.getStatus())
                .body(new ErrorResponse(
                        ErrorCode.NOT_MATCH_PASSWORD.getStatus().value(),
                        ErrorCode.NOT_MATCH_PASSWORD.getCode(),
                        ErrorCode.NOT_MATCH_PASSWORD.getMessage())
                );
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<ErrorResponse> handleInternalAuthenticationServiceException(
            InternalAuthenticationServiceException e
    ) {
        Throwable cause = e.getCause();

        if (cause instanceof GlobalException globalException &&
            globalException.getErrorCode() == ErrorCode.USER_NOT_FOUND) {

            return ResponseEntity
                    .status(ErrorCode.USER_NOT_FOUND.getStatus()) // NOT_FOUND(404)
                    .body(new ErrorResponse(
                            ErrorCode.USER_NOT_FOUND.getStatus().value(),
                            ErrorCode.USER_NOT_FOUND.getCode(),
                            ErrorCode.USER_NOT_FOUND.getMessage())
                    );
        }

        return handleUnexpectedException(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        e.getMessage())
                );
    }
}