package com.practice.boardproject.domain.comment.service;


import com.practice.boardproject.domain.comment.dto.request.CreateCommentRequest;
import com.practice.boardproject.domain.comment.dto.request.DeleteCommentRequest;
import com.practice.boardproject.domain.comment.dto.request.UpdateCommentRequest;

public interface CommandCommentService {

  void createComment(Long boardId, CreateCommentRequest request);

  void updateComment(Long commentId, UpdateCommentRequest request);

  void deleteComment(Long commentId, DeleteCommentRequest request);

}
