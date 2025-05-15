package com.practice.boardproject.post.dto.response;

import java.util.List;

public record PostListResponse(
        List<PostDetailResponse> posts,
        int size
) {
}
