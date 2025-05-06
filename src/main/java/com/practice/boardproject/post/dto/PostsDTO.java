package com.practice.boardproject.post.dto;

import com.practice.boardproject.global.page.PagingButton;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter @Setter
public class PostsDTO {

    private List<PostDetailDTO> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private PagingButton paging;

    public PostsDTO(Page<PostDetailDTO> page, PagingButton paging) {
        this.content = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.paging = paging;
    }
}
