package com.practice.boardproject.domain.comment.service;

import com.practice.boardproject.domain.board.Board;
import com.practice.boardproject.domain.board.repository.BoardRepository;
import com.practice.boardproject.domain.comment.Comment;
import com.practice.boardproject.domain.comment.dto.request.CreateCommentRequest;
import com.practice.boardproject.domain.comment.dto.request.DeleteCommentRequest;
import com.practice.boardproject.domain.comment.dto.request.UpdateCommentRequest;
import com.practice.boardproject.domain.comment.repository.CommentRepository;
import com.practice.boardproject.domain.member.Member;
import com.practice.boardproject.domain.member.repository.MemberRepository;
import com.practice.boardproject.global.api_response.exception.GlobalException;
import com.practice.boardproject.global.api_response.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommandCommentServiceImpl implements CommandCommentService {

  private final CommentRepository commentRepository;
  private final BoardRepository boardRepository;
  private final MemberRepository memberRepository;

  public void createComment(Long boardId, CreateCommentRequest request) {
    Board board = boardRepository.findById(boardId)
      .orElseThrow(() -> new GlobalException(ErrorStatus.NOT_FOUND, "존재하지 않는 게시글입니다."));
    Member author = memberRepository.findMemberByUsername(request.username())
      .orElseThrow(() -> new GlobalException(ErrorStatus.NOT_FOUND, "존재하지 않는 사용자입니다."));

    Comment comment = Comment.builder()
      .author(author)
      .content(request.content())
      .build();
    comment.setBoard(board);

    commentRepository.save(comment);
  }

  public void updateComment(Long commentId, UpdateCommentRequest request) {
    Comment comment = validate(commentId, request.password());
    comment.update(request.content());
  }

  public void deleteComment(Long commentId, DeleteCommentRequest request) {
    Comment comment = validate(commentId, request.password());
    commentRepository.delete(comment);
  }

  private Comment validate(long commentId, String password) {
    Comment comment = commentRepository.findById(commentId)
      .orElseThrow(() -> new GlobalException(ErrorStatus.NOT_FOUND, "존재하지 않는 댓글입니다."));
    if (!comment.getAuthor().getPassword().equals(password)) {
      throw new GlobalException(ErrorStatus.BAD_REQUEST, "비밀번호가 올바르지 않습니다.");
    }
    return comment;
  }

}

