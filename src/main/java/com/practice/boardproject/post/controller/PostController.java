package com.practice.boardproject.post.controller;

import com.practice.boardproject.global.exception.CustomException;
import com.practice.boardproject.global.page.Pagenation;
import com.practice.boardproject.global.page.PagingButton;
import com.practice.boardproject.post.dto.PostDTO;
import com.practice.boardproject.post.dto.PostDetailDTO;
import com.practice.boardproject.post.dto.PostsDTO;
import com.practice.boardproject.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> registPost(@RequestBody PostDTO newPost) throws CustomException {

        postService.registPost(newPost);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{postNo}")
    @Operation(summary = "게시글 삭제", description = "게시글 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> removePost(@PathVariable int postNo) throws CustomException {

        postService.removePost(postNo);

        return ResponseEntity.ok().build();
    }

    // 게시글 수정
    @PutMapping("/posts/{postNo}")
    @Operation(summary = "게시글 수정", description = "게시글 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> modifyPost(@PathVariable int postNo, @RequestBody PostDTO postDTO) throws CustomException {

        postService.modifyPost(postNo, postDTO);

        return ResponseEntity.ok().build();
    }

    // 게시글 상세 조회
    @GetMapping("/posts/{postNo}")
    @Operation(summary = "게시글 상세 조회", description = "게시글 상세 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<PostDetailDTO> getPostDetail(@PathVariable int postNo) throws CustomException {

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
