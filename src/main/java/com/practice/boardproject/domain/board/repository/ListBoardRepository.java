package com.practice.boardproject.domain.board.repository;

import static com.practice.boardproject.domain.board.QBoard.board;

import com.practice.boardproject.domain.board.dto.request.SearchBoardRequest;
import com.practice.boardproject.domain.board.dto.response.ListBoardDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class ListBoardRepository {

  private final JPAQueryFactory queryFactory;

  public Page<ListBoardDto> getBoardList(SearchBoardRequest request) {
    List<ListBoardDto> boardList =
      queryFactory
        .select(
          Projections.fields(
            ListBoardDto.class,
            board.id.as("boardId"),
            board.title,
            board.author.username.as("author"),
            board.createdAt
          )
        )
        .from(board)
        .where(searchKeyword(request.searchKeyword()))
        .offset(request.pageRequest().getOffset())
        .limit(request.pageRequest().getPageSize())
        .orderBy(board.createdAt.desc())
        .fetch();

    JPAQuery<Long> countQuery = queryFactory
      .select(board.count())
      .from(board)
      .where(searchKeyword(request.searchKeyword()));

    return PageableExecutionUtils.getPage(boardList, request.pageRequest(), countQuery::fetchOne);

  }

  /**
   * 키워드 검색(제목, 내용)
   */
  private Predicate searchKeyword(String searchKeyword) {
    if (!StringUtils.hasText(searchKeyword)) {
      return null;
    }
    BooleanBuilder builder = new BooleanBuilder();
    builder.or(board.title.contains(searchKeyword));
    builder.or(board.content.contains(searchKeyword));
    return builder;
  }

}
