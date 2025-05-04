package com.practice.boardproject.domain.comment.repository;


import static com.practice.boardproject.domain.comment.QComment.comment;

import com.practice.boardproject.domain.comment.controller.response.ListCommentDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListCommentRepository {

  private final JPAQueryFactory queryFactory;

  public List<ListCommentDto> getCommentList(Long boardId) {
    return queryFactory.select(
        Projections.fields(
          ListCommentDto.class,
          comment.id.as("commentId"),
          comment.content,
          comment.author.username,
          comment.createdAt
        )
      )
      .from(comment)
      .where(comment.board.id.eq(boardId))
      .orderBy(comment.createdAt.desc())
      .fetch();
  }

}

