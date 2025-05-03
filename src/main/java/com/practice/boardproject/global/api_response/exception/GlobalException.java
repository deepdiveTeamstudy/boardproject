package com.practice.boardproject.global.api_response.exception;

import com.practice.boardproject.global.api_response.dto.ApiDto;
import com.practice.boardproject.global.api_response.status.ErrorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalException extends RuntimeException {

    private final ErrorStatus code;

    public GlobalException(ErrorStatus errorStatus, String message) {
        super(errorStatus.getMessage(message));
        this.code = errorStatus;
    }

    public ApiDto getErrorReasonHttpStatus() {
        return this.code.getReasonHttpStatus();
    }
}
