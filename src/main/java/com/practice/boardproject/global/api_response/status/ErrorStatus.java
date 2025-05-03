package com.practice.boardproject.global.api_response.status;

import com.practice.boardproject.global.api_response.dto.ApiDto;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {


    BAD_REQUEST(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString()),
    NOT_FOUND(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.toString());


    private final HttpStatus httpStatus;
    private final String code;
    private String message;


    public String getMessage(String message) {
        this.message = message;
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    public ApiDto getReasonHttpStatus() {
        return ApiDto.builder()
            .message(message)
            .code(code)
            .isSuccess(false)
            .httpStatus(httpStatus)
            .build();
    }
}
