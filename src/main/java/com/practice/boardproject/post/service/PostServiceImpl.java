package com.practice.boardproject.post.service;

import static org.springframework.data.domain.Sort.Direction.DESC;

import com.practice.boardproject.global.exception.ErrorCode;
import com.practice.boardproject.global.exception.GlobalException;
import com.practice.boardproject.member.domain.Member;
import com.practice.boardproject.member.service.MemberService;
import com.practice.boardproject.post.domain.Post;
import com.practice.boardproject.post.dto.request.PostCreateRequest;
import com.practice.boardproject.post.dto.request.PostUpdateRequest;
import com.practice.boardproject.post.dto.response.PostCreateResponse;
import com.practice.boardproject.post.dto.response.PostDetailResponse;
import com.practice.boardproject.post.dto.response.PostListResponse;
import com.practice.boardproject.post.dto.response.PostUpdateResponse;
import com.practice.boardproject.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public PostDetailResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getUsername(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public PostListResponse getPosts() {
        List<Post> posts = postRepository.findAll(Sort.by(DESC, "createdAt"));

        List<PostDetailResponse> postResponses = posts.stream()
                .map(post -> new PostDetailResponse(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getAuthor().getUsername(),
                        post.getCreatedAt(),
                        post.getUpdatedAt())
                ).toList();

        return new PostListResponse(postResponses, posts.size());
    }

    public PostCreateResponse createPost(PostCreateRequest request) {
        Member author = memberService.findMemberByUsername(request.username());

        Post savedPost = postRepository.save(
                Post.builder()
                        .author(author)
                        .title(request.title())
                        .content(request.content())
                        .build()
        );

        return new PostCreateResponse(
                savedPost.getTitle(),
                savedPost.getContent(),
                savedPost.getAuthor().getUsername(),
                savedPost.getCreatedAt()
        );
    }

    public PostUpdateResponse updatePost(PostUpdateRequest request) {
        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        post.update(request.title(), request.content());

        return new PostUpdateResponse(
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getUsername(),
                post.getUpdatedAt()
        );
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        postRepository.deleteById(post.getId());
    }
}