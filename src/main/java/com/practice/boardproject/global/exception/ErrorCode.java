package com.practice.boardproject.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 회원 관련 에러
    MEMBER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),

    // 게시글 관련 에러
    POST_NOT_FOUND(404, "게시글을 찾을 수 없습니다."),

    // 공통 에러
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생했습니다.");

    private final int status;
    private final String message;

}
