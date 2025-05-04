package com.practice.boardproject.domain.comment.service;

import static org.junit.jupiter.api.Assertions.*;

import com.practice.boardproject.domain.board.Board;
import com.practice.boardproject.domain.board.repository.BoardRepository;
import com.practice.boardproject.domain.comment.Comment;
import com.practice.boardproject.domain.comment.dto.request.DeleteCommentRequest;
import com.practice.boardproject.domain.comment.dto.request.UpdateCommentRequest;
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
class CommandCommentServiceImplTest {

  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private BoardRepository boardRepository;
  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private CommandCommentServiceImpl commandCommentServiceImpl;

  @Test
  @DisplayName("타인의 댓글은 수정/삭제할 수 없다.")
  void 댓글_권한_검증() {
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

    Comment comment = Comment.builder()
      .author(me)
      .content("comment")
      .build();
    comment.setBoard(board);
    Comment savedComment = commentRepository.save(comment);

    UpdateCommentRequest updateRequest = new UpdateCommentRequest("not me", "update");
    assertThrows(GlobalException.class,
      () -> commandCommentServiceImpl.updateComment(savedComment.getId(), updateRequest));
    DeleteCommentRequest deleteRequest = new DeleteCommentRequest("not me");
    assertThrows(GlobalException.class,
      () -> commandCommentServiceImpl.deleteComment(savedComment.getId(), deleteRequest));

  }
}