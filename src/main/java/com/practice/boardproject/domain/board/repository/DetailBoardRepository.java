package com.practice.boardproject.domain.board.repository;

import static com.practice.boardproject.domain.board.QBoard.board;

import com.practice.boardproject.domain.board.Board;
import com.practice.boardproject.domain.board.QBoard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailBoardRepository {

  private final JPAQueryFactory queryFactory;

  public Board queryDslTest() {
    return queryFactory
      .selectFrom(board)
      .limit(1)
      .fetchOne();
  }
}
