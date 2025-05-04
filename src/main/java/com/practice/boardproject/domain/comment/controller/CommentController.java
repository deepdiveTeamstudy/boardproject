package com.practice.boardproject.domain.comment.controller;

import com.practice.boardproject.domain.comment.controller.request.CreateCommentRequest;
import com.practice.boardproject.domain.comment.controller.request.DeleteCommentRequest;
import com.practice.boardproject.domain.comment.controller.request.UpdateCommentRequest;
import com.practice.boardproject.domain.comment.controller.response.ListCommentResponse;
import com.practice.boardproject.domain.comment.service.CommandCommentService;
import com.practice.boardproject.domain.comment.service.ReadCommentService;
import com.practice.boardproject.global.api_response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "게시글 댓글 관련 API")
public class CommentController {

  private final CommandCommentService commandCommentService;
  private final ReadCommentService readCommentService;

  @GetMapping("/boards/{boardId}/comments")
  @Operation(summary = "게시글 댓글 목록 조회")
  public ApiResponse<ListCommentResponse> getCommentList(
    @PathVariable("boardId") Long boardId) {
    return ApiResponse.ok(readCommentService.getCommentList(boardId));
  }

  @PostMapping("/boards/{boardId}/comments")
  @Operation(summary = "게시글 댓글 생성")
  public ApiResponse<Void> createComment(
    @PathVariable("boardId") Long boardId,
    @RequestBody @Valid CreateCommentRequest request) {
    commandCommentService.createComment(boardId, request);
    return ApiResponse.ok();
  }

  @PatchMapping("/boards/comments/{commentId}")
  @Operation(summary = "게시글 댓글 수정")
  public ApiResponse<Void> updateComment(
    @PathVariable("commentId") Long commentId,
    @RequestBody @Valid UpdateCommentRequest request) {
    commandCommentService.updateComment(commentId, request);
    return ApiResponse.ok();
  }

  @DeleteMapping("/boards/comments/{commentId}")
  @Operation(summary = "게시글 댓글 삭제")
  public ApiResponse<Void> deleteComment(
    @PathVariable("commentId") Long commentId,
    @RequestBody @Valid DeleteCommentRequest request) {
    commandCommentService.deleteComment(commentId, request);
    return ApiResponse.ok();
  }


}
