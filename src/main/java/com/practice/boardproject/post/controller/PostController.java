package com.practice.boardproject.post.controller;

import com.practice.boardproject.post.dto.PostDTO;
import com.practice.boardproject.post.dto.PostDetailDTO;
import com.practice.boardproject.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 등록
    @PostMapping("/posts")
    public ResponseEntity<?> registPost(@RequestBody PostDTO newPost) {

        try {
            postService.registPost(newPost);

            return ResponseEntity.ok("게시글 등록 성공");

        } catch (Exception e) {
            log.error("error : " + e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("게시글 등록 실패 : " + e.getMessage());
        }
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{postNo}")
    public ResponseEntity<?> removePost(@PathVariable int postNo) {

        try {
            postService.removePost(postNo);

            return ResponseEntity.ok("게시글 삭제 성공");

        } catch (Exception e) {
            log.error("error : " + e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("게시글 삭제 실패 : " + e.getMessage());
        }
    }

    // 게시글 수정
    @PutMapping("/posts/{postNo}")
    public ResponseEntity<?> modifyPost(@PathVariable int postNo, @RequestBody PostDTO postDTO) {

        try {
            postService.modifyPost(postNo, postDTO);

            return ResponseEntity.ok("게시글 수정 성공");
        } catch (Exception e) {
            log.error("error : " + e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("게시글 수정 실패 : " + e.getMessage());
        }
    }

    // 게시글 상세 조회
    @GetMapping("/posts/{postNo}")
    public ResponseEntity<PostDetailDTO> getPostDetail(@PathVariable int postNo) {

        PostDetailDTO postDetail = postService.getPostDetail(postNo);
        return ResponseEntity.ok(postDetail);
    }
}
