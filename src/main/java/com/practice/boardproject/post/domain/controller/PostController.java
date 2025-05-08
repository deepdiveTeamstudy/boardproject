package com.practice.boardproject.post.domain.controller;

import com.practice.boardproject.post.domain.dto.request.PostCreateRequest;
import com.practice.boardproject.post.domain.dto.request.PostUpdateRequest;
import com.practice.boardproject.post.domain.dto.response.PostCreateResponse;
import com.practice.boardproject.post.domain.dto.response.PostDetailResponse;
import com.practice.boardproject.post.domain.dto.response.PostListResponse;
import com.practice.boardproject.post.domain.dto.response.PostUpdateResponse;
import com.practice.boardproject.post.domain.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponse> getPost(@PathVariable Long postId) {
        PostDetailResponse post = postService.getPost(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<PostListResponse> getPosts() {
        PostListResponse posts = postService.getPosts();
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/new")
    public ResponseEntity<PostCreateResponse> createPost(@Valid @RequestBody PostCreateRequest request) {
        PostCreateResponse response = postService.createPost(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<PostUpdateResponse> updatePost(@Valid @RequestBody PostUpdateRequest request) {
        PostUpdateResponse response = postService.updatePost(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }
}
