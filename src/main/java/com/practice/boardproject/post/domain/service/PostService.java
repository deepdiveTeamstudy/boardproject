package com.practice.boardproject.post.domain.service;

import com.practice.boardproject.member.domain.Member;
import com.practice.boardproject.member.repository.MemberRepository;
import com.practice.boardproject.post.domain.Post;
import com.practice.boardproject.post.domain.dto.PostCreateRequest;
import com.practice.boardproject.post.domain.dto.PostCreateResponse;
import com.practice.boardproject.post.domain.exception.NotFoundUserException;
import com.practice.boardproject.post.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostCreateResponse createPost(PostCreateRequest request) {
        Member author = memberRepository.findByUsername(request.username())
                .orElseThrow(() -> new NotFoundUserException(request.username()));
        Post post = Post.create(request.title(), request.content(), author);
        return new PostCreateResponse(post.getTitle(), post.getContent(), author.getUsername(), post.getCreatedAt());
    }
}
