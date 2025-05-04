package com.practice.boardproject.domain.comment.service;


import com.practice.boardproject.domain.comment.controller.response.ListCommentResponse;

public interface ReadCommentService {

  ListCommentResponse getCommentList(Long boardId);

}
