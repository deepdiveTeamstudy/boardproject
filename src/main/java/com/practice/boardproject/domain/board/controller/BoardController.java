package com.practice.boardproject.domain.board.controller;

import com.practice.boardproject.domain.board.Board;
import com.practice.boardproject.domain.board.repository.DetailBoardRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Tag(name = "게시글 관련 API")
public class BoardController {


  private final DetailBoardRepository detailBoardRepository;

  @GetMapping()
  @Operation(summary = "Swagger Test")
  public ResponseEntity<Long> test() {
    return ResponseEntity.ok(detailBoardRepository.queryDslTest().getId());
  }

}
