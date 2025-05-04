package com.practice.boardproject.domain.board.repository;

import static com.practice.boardproject.domain.board.QBoard.board;

import com.practice.boardproject.domain.board.dto.response.DetailBoardResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailBoardRepository {

  private final JPAQueryFactory queryFactory;

  public DetailBoardResponse getBoard(Long boardId) {
    return queryFactory.select(Projections.fields(
        DetailBoardResponse.class,
        board.title,
        board.content,
        board.author.username,
        board.createdAt
      ))
      .from(board)
      .where(board.id.eq(boardId))
      .fetchOne();
  }

}
