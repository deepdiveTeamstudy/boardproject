package com.practice.boardproject.post.service;

import com.practice.boardproject.post.dto.request.PostCreateRequest;
import com.practice.boardproject.post.dto.request.PostUpdateRequest;
import com.practice.boardproject.post.dto.response.PostCreateResponse;
import com.practice.boardproject.post.dto.response.PostDetailResponse;
import com.practice.boardproject.post.dto.response.PostListResponse;
import com.practice.boardproject.post.dto.response.PostUpdateResponse;

public interface PostService {
    PostDetailResponse getPost(Long postId);

    PostListResponse getPosts();

    PostCreateResponse createPost(PostCreateRequest request);

    PostUpdateResponse updatePost(PostUpdateRequest request);

    void deletePost(Long postId);
}