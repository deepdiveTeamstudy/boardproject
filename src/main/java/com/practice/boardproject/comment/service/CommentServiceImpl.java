package com.practice.boardproject.comment.service;

import com.practice.boardproject.comment.domain.Comment;
import com.practice.boardproject.comment.dto.request.CommentCreateRequest;
import com.practice.boardproject.comment.dto.request.CommentUpdateRequest;
import com.practice.boardproject.comment.dto.response.CommentCreateResponse;
import com.practice.boardproject.comment.dto.response.CommentUpdateResponse;
import com.practice.boardproject.comment.repository.CommentRepository;
import com.practice.boardproject.global.exception.ErrorCode;
import com.practice.boardproject.global.exception.GlobalException;
import com.practice.boardproject.global.util.SecurityUtils;
import com.practice.boardproject.member.domain.Member;
import com.practice.boardproject.member.repository.MemberRepository;
import com.practice.boardproject.post.domain.Post;
import com.practice.boardproject.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public CommentCreateResponse createComment(CommentCreateRequest request) {

        String currentMemberName = SecurityUtils.getCurrentMemberName();

        Member author = memberRepository.findByUsername(currentMemberName)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        Comment newComment = commentRepository.save(
                Comment.builder()
                        .author(author)
                        .post(post)
                        .content(request.content())
                        .build()
        );

        return new CommentCreateResponse(
                newComment.getContent(),
                newComment.getAuthor().getUsername(),
                newComment.getCreatedAt()
        );
    }

    public CommentUpdateResponse updateComment(CommentUpdateRequest request) {

        String currentMemberName = SecurityUtils.getCurrentMemberName();

        Comment comment = commentRepository.findById(request.commentId())
                .orElseThrow(() -> new GlobalException(ErrorCode.COMMENT_NOT_FOUND));

        if(!comment.getAuthor().getUsername().equals(currentMemberName)) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        comment.update(request.content());

        return new CommentUpdateResponse(
                comment.getContent(),
                comment.getAuthor().getUsername(),
                comment.getUpdatedAt()
        );
    }

    @Override
    public void deleteComment(Long commentId) {

    }
}
