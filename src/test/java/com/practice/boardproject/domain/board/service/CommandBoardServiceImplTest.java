package com.practice.boardproject.domain.board.service;

import static org.junit.jupiter.api.Assertions.*;

import com.practice.boardproject.domain.board.Board;
import com.practice.boardproject.domain.board.dto.request.DeleteBoardRequest;
import com.practice.boardproject.domain.board.dto.request.UpdateBoardRequest;
import com.practice.boardproject.domain.board.repository.BoardRepository;
import com.practice.boardproject.domain.comment.Comment;
import com.practice.boardproject.domain.comment.repository.CommentRepository;
import com.practice.boardproject.domain.member.Member;
import com.practice.boardproject.domain.member.repository.MemberRepository;
import com.practice.boardproject.global.api_response.exception.GlobalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback
class CommandBoardServiceImplTest {

  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private BoardRepository boardRepository;
  @Autowired
  private CommandBoardServiceImpl commandBoardServiceImpl;
  @Autowired
  private CommentRepository commentRepository;

  @Test
  @DisplayName("자신의 게시글은 수정/삭제할 수 있다.")
  void 본인_게시글_수정_및_삭제() {
    Member me = memberRepository.save(
      Member.builder()
        .username("me")
        .password("me")
        .build()
    );

    Board board = boardRepository.save(
      Board.builder()
        .author(me)
        .title("title")
        .content("content")
        .build()
    );

    UpdateBoardRequest updateRequest = new UpdateBoardRequest("me", "update", "update");
    commandBoardServiceImpl.updateBoard(board.getId(), updateRequest);

    Board updatedBoard = boardRepository.findById(board.getId()).orElseThrow();
    assertEquals("update", updatedBoard.getTitle());
    assertEquals("update", updatedBoard.getContent());

    DeleteBoardRequest deleteRequest = new DeleteBoardRequest("me");
    commandBoardServiceImpl.deleteBoard(board.getId(), deleteRequest);
    assertFalse(boardRepository.findById(board.getId()).isPresent());
  }

  @Test
  @DisplayName("타인의 게시글은 수정/삭제할 수 없다.")
  void 게시글_권한_검증() {
    Member me = memberRepository.save(
      Member.builder()
        .username("me")
        .password("me")
        .build()
    );

    Board board = boardRepository.save(
      Board.builder()
        .author(me)
        .title("title")
        .content("content")
        .build()
    );

    UpdateBoardRequest updateRequest = new UpdateBoardRequest("not me", "update", "update");
    assertThrows(GlobalException.class,
      () -> commandBoardServiceImpl.updateBoard(board.getId(), updateRequest));
    DeleteBoardRequest deleteRequest = new DeleteBoardRequest("not me");
    assertThrows(GlobalException.class,
      () -> commandBoardServiceImpl.deleteBoard(board.getId(), deleteRequest));

  }


  @Test
  @DisplayName("게시글 삭제 시 댓글도 함께 삭제된다.")
  void 게시글_삭제_시_댓글_자동_삭제() {
    Member author = memberRepository.save(
      Member.builder()
        .username("me")
        .password("me")
        .build()
    );

    Board board = boardRepository.save(
      Board.builder()
        .author(author)
        .title("title")
        .content("content")
        .build()
    );

    Comment comment1 = Comment.builder()
      .author(author)
      .content("1")
      .build();
    comment1.setBoard(board);

    Comment comment2 = Comment.builder()
      .author(author)
      .content("2")
      .build();
    comment2.setBoard(board);

    commentRepository.save(comment1);
    commentRepository.save(comment2);

    Long boardId = board.getId();
    DeleteBoardRequest deleteRequest = new DeleteBoardRequest("me");
    commandBoardServiceImpl.deleteBoard(boardId, deleteRequest);

    assertFalse(boardRepository.findById(boardId).isPresent());
    assertFalse(commentRepository.findById(comment1.getId()).isPresent());
    assertFalse(commentRepository.findById(comment2.getId()).isPresent());
  }

}