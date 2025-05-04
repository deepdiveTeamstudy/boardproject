package com.practice.boardproject.domain.comment.service;

import com.practice.boardproject.domain.comment.controller.response.ListCommentResponse;
import com.practice.boardproject.domain.comment.repository.ListCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReadCommentServiceImpl implements ReadCommentService {

  private final ListCommentRepository listCommentRepository;

  public ListCommentResponse getCommentList(Long boardId) {
    return ListCommentResponse.builder()
      .commentList(listCommentRepository.getCommentList(boardId))
      .build();
  }

}
