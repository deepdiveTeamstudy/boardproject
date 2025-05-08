package com.practice.boardproject.post.domain.service;

import com.practice.boardproject.post.domain.dto.request.PostCreateRequest;
import com.practice.boardproject.post.domain.dto.request.PostUpdateRequest;
import com.practice.boardproject.post.domain.dto.response.PostCreateResponse;
import com.practice.boardproject.post.domain.dto.response.PostDetailResponse;
import com.practice.boardproject.post.domain.dto.response.PostListResponse;
import com.practice.boardproject.post.domain.dto.response.PostUpdateResponse;

public interface PostService {
    PostDetailResponse getPost(Long postId);

    PostListResponse getPosts();

    PostCreateResponse createPost(PostCreateRequest request);

    PostUpdateResponse updatePost(PostUpdateRequest request);

    void deletePost(Long postId);
}