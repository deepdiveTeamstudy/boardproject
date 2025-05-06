package com.practice.boardproject.post.domain.controller;

import com.practice.boardproject.post.domain.dto.PostCreateRequest;
import com.practice.boardproject.post.domain.dto.PostCreateResponse;
import com.practice.boardproject.post.domain.dto.PostUpdateRequest;
import com.practice.boardproject.post.domain.dto.PostUpdateResponse;
import com.practice.boardproject.post.domain.service.PostService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/posts")
@RequiredArgsConstructor
@Transactional
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostCreateResponse> createPost(@Valid @RequestBody PostCreateRequest request) {
        PostCreateResponse response = postService.createPost(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<PostUpdateResponse> updatePost(@Valid @RequestBody PostUpdateRequest request) {
        PostUpdateResponse response = postService.updatePost(request);
        return ResponseEntity.ok(response);
    }
}
