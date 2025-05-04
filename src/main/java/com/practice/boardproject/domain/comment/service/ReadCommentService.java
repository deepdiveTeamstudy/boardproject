package com.practice.boardproject.domain.comment.service;


import com.practice.boardproject.domain.comment.dto.response.ListCommentResponse;

public interface ReadCommentService {

  ListCommentResponse getCommentList(Long boardId);

}
