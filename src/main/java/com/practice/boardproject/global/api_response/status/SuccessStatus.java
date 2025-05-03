package com.practice.boardproject.global.api_response.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus {
    OK(HttpStatus.OK, HttpStatus.OK.toString(), "성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
