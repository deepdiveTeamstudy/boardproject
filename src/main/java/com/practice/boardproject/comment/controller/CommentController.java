package com.practice.boardproject.comment.controller;


import com.practice.boardproject.comment.dto.request.CommentCreateRequest;
import com.practice.boardproject.comment.dto.request.CommentUpdateRequest;
import com.practice.boardproject.comment.dto.response.CommentCreateResponse;
import com.practice.boardproject.comment.dto.response.CommentUpdateResponse;
import com.practice.boardproject.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentCreateResponse> createComment(@Valid @RequestBody CommentCreateRequest request){
        CommentCreateResponse response = commentService.createComment(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommentUpdateResponse> updateComment(@Valid @RequestBody CommentUpdateRequest request){
        CommentUpdateResponse response = commentService.updateComment(request);
        return ResponseEntity.ok(response);
    }

}
