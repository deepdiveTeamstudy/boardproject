package com.practice.boardproject.domain.board.controller;

import com.practice.boardproject.domain.board.dto.request.CreateBoardRequest;
import com.practice.boardproject.domain.board.dto.request.DeleteBoardRequest;
import com.practice.boardproject.domain.board.dto.request.SearchBoardRequest;
import com.practice.boardproject.domain.board.dto.request.UpdateBoardRequest;
import com.practice.boardproject.domain.board.dto.response.DetailBoardResponse;
import com.practice.boardproject.domain.board.dto.response.ListBoardResponse;
import com.practice.boardproject.domain.board.service.CommandBoardService;
import com.practice.boardproject.domain.board.service.ReadBoardService;
import com.practice.boardproject.global.api_response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
@Tag(name = "게시글 관련 API")
public class BoardController {

  private final CommandBoardService commandBoardService;
  private final ReadBoardService readBoardService;

  @PostMapping
  @Operation(summary = "게시글 생성")
  public ApiResponse<Void> createBoard(
    @RequestBody @Valid CreateBoardRequest request) {
    commandBoardService.createBoard(request);
    return ApiResponse.ok();
  }

  @GetMapping
  @Operation(summary = "게시글 목록 조회")
  public ApiResponse<ListBoardResponse> getBoardList(
    @ParameterObject @ModelAttribute @Valid SearchBoardRequest request) {
    return ApiResponse.ok(readBoardService.getBoardList(request));
  }

  @GetMapping("/{boardId}")
  @Operation(summary = "게시글 상세 조회")
  public ApiResponse<DetailBoardResponse> getBoard(@PathVariable("boardId") Long boardId) {
    return ApiResponse.ok(readBoardService.getBoard(boardId));
  }

  @PatchMapping("/{boardId}")
  @Operation(summary = "게시글 수정")
  public ApiResponse<Void> updateBoard(
    @PathVariable("boardId") long boardId,
    @RequestBody @Valid UpdateBoardRequest request) {
    commandBoardService.updateBoard(boardId, request);
    return ApiResponse.ok();
  }

  @DeleteMapping("/{boardId}")
  @Operation(summary = "게시글 삭제")
  public ApiResponse<Void> deleteBoard(
    @PathVariable("boardId") long boardId,
    @RequestBody @Valid DeleteBoardRequest request) {
    commandBoardService.deleteBoard(boardId, request);
    return ApiResponse.ok();
  }

}
