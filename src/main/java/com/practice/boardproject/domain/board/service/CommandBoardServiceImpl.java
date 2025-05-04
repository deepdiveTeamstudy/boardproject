package com.practice.boardproject.domain.board.service;

import com.practice.boardproject.domain.board.Board;
import com.practice.boardproject.domain.board.dto.request.CreateBoardRequest;
import com.practice.boardproject.domain.board.dto.request.DeleteBoardRequest;
import com.practice.boardproject.domain.board.dto.request.UpdateBoardRequest;
import com.practice.boardproject.domain.board.repository.BoardRepository;
import com.practice.boardproject.domain.member.Member;
import com.practice.boardproject.domain.member.repository.MemberRepository;
import com.practice.boardproject.global.api_response.exception.GlobalException;
import com.practice.boardproject.global.api_response.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommandBoardServiceImpl implements CommandBoardService {

  private final MemberRepository memberRepository;
  private final BoardRepository boardRepository;

  public void createBoard(CreateBoardRequest request) {
    Member author = memberRepository.findMemberByUsername(request.username())
      .orElseThrow(() -> new GlobalException(ErrorStatus.NOT_FOUND, "존재하지 않는 사용자입니다."));

    boardRepository.save(
      Board.builder()
        .author(author)
        .title(request.title())
        .content(request.content())
        .build()
    );
  }

  public void updateBoard(long boardId, UpdateBoardRequest request) {
    Board board = validate(boardId, request.password());
    board.update(request.title(), request.content());
  }

  public void deleteBoard(long boardId, DeleteBoardRequest request) {
    Board board = validate(boardId, request.password());
    boardRepository.delete(board);
  }


  private Board validate(long boardId, String password) {
    Board board = boardRepository.findById(boardId)
      .orElseThrow(() -> new GlobalException(ErrorStatus.NOT_FOUND, "존재하지 않는 게시글입니다."));
    if (!board.getAuthor().getPassword().equals(password)) {
      throw new GlobalException(ErrorStatus.BAD_REQUEST, "비밀번호가 올바르지 않습니다.");
    }
    return board;
  }
}
