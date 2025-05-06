package com.practice.boardproject.post.controller;

import com.practice.boardproject.global.page.Pagenation;
import com.practice.boardproject.global.page.PagingButton;
import com.practice.boardproject.post.dto.PostDTO;
import com.practice.boardproject.post.dto.PostDetailDTO;
import com.practice.boardproject.post.dto.PostsDTO;
import com.practice.boardproject.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "PostController")
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
    @Operation(summary = "게시글 등록", description = "게시글 등록 API")
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
    @Operation(summary = "게시글 삭제", description = "게시글 삭제 API")
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
    @Operation(summary = "게시글 수정", description = "게시글 수정 API")
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
    @Operation(summary = "게시글 상세 조회", description = "게시글 상세 조회 API")
    public ResponseEntity<PostDetailDTO> getPostDetail(@PathVariable int postNo) {

        PostDetailDTO postDetail = postService.getPostDetail(postNo);
        return ResponseEntity.ok(postDetail);
    }

    // 게시글 전체 조회 with 페이징
    @GetMapping("/posts")
    @Operation(summary = "게시글 전체 조회", description = "페이징 처리 된 게시글 전체 조회 API")
    public ResponseEntity<PostsDTO> getAllPosts(@PageableDefault Pageable pageable) {

        Page<PostDetailDTO> posts = postService.getAllPosts(pageable);
        PagingButton paging = Pagenation.getPagingButtonInfo(posts);

        PostsDTO pagingPosts = new PostsDTO(posts, paging);

        return ResponseEntity.ok(pagingPosts);
    }


}
