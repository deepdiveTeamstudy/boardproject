package com.practice.boardproject.member.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record SignUpRequest(
        @NotEmpty
        String username,
        @NotEmpty
        String password
) {
}
