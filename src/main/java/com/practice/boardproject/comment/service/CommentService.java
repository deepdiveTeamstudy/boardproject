package com.practice.boardproject.comment.service;

import com.practice.boardproject.comment.dto.request.CommentCreateRequest;
import com.practice.boardproject.comment.dto.request.CommentUpdateRequest;
import com.practice.boardproject.comment.dto.response.CommentCreateResponse;
import com.practice.boardproject.comment.dto.response.CommentUpdateResponse;

public interface CommentService {

    CommentCreateResponse createComment(CommentCreateRequest commentCreateRequest);

    CommentUpdateResponse updateComment(CommentUpdateRequest commentUpdateRequest);

    void deleteComment(Long commentId);
}
