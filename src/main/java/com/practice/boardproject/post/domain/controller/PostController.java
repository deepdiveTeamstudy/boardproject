package com.practice.boardproject.post.domain.controller;

import com.practice.boardproject.post.domain.dto.PostCreateRequest;
import com.practice.boardproject.post.domain.dto.PostCreateResponse;
import com.practice.boardproject.post.domain.dto.PostDetailResponse;
import com.practice.boardproject.post.domain.dto.PostUpdateRequest;
import com.practice.boardproject.post.domain.dto.PostUpdateResponse;
import com.practice.boardproject.post.domain.service.PostService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/posts")
@RequiredArgsConstructor
@Transactional
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponse> getPost(@PathVariable Long postId) {
        PostDetailResponse post = postService.getPost(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping()
    public ResponseEntity<List<PostDetailResponse>> getPosts() {
        List<PostDetailResponse> posts = postService.getPosts();
        return ResponseEntity.ok(posts);
    }

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

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }
}
