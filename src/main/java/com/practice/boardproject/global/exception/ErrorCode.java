package com.practice.boardproject.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 공통 에러
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "INVALID_INPUT", "필수 입력값이 누락되었습니다."),

    // 사용자 관련 에러
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "존재하지 않는 사용자입니다."),

    // 중복 사용자 에러
    DUPLICATE_USER(HttpStatus.CONFLICT, "DUPLICATE_USER", "중복된 사용자입니다."),

    // 게시글 관련 에러
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "존재하지 않는 게시글입니다."),

    // 패스워드 불일치 에러
    NOT_MATCH_PASSWORD(HttpStatus.UNAUTHORIZED, "NOT_MATCH_PASSWORD", "비밀번호가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}