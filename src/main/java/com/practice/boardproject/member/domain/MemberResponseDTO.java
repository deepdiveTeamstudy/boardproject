package com.practice.boardproject.member.domain;

import lombok.Builder;

@Builder
public class MemberResponseDTO {
    private String username;

    public MemberResponseDTO() {}

    public MemberResponseDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
