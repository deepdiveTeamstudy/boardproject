package com.practice.boardproject.post.dto;

import com.practice.boardproject.member.domain.Member;
import lombok.Builder;

@Builder
public class PostRequestDTO {
    private Long id;
    private String title;
    private String content;
    private String username;

    public PostRequestDTO() {}

    public PostRequestDTO(Long id, String title, String content, String username) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }
}
