package com.practice.boardproject.post.domain.service;

import static org.springframework.data.domain.Sort.Direction.*;

import com.practice.boardproject.member.domain.Member;
import com.practice.boardproject.member.repository.MemberRepository;
import com.practice.boardproject.post.domain.Post;
import com.practice.boardproject.post.domain.dto.PostCreateRequest;
import com.practice.boardproject.post.domain.dto.PostCreateResponse;
import com.practice.boardproject.post.domain.dto.PostDetailResponse;
import com.practice.boardproject.post.domain.dto.PostUpdateRequest;
import com.practice.boardproject.post.domain.dto.PostUpdateResponse;
import com.practice.boardproject.post.domain.exception.NotFoundPostException;
import com.practice.boardproject.member.error.NotFoundUserException;
import com.practice.boardproject.post.domain.repository.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostDetailResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException(postId));

        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getUsername(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    public List<PostDetailResponse> getPosts() {
    List<Post> posts = postRepository.findAll(Sort.by(DESC, "createdAt"));
    
    return posts.stream()
            .map(post -> new PostDetailResponse(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getAuthor().getUsername(),
                    post.getCreatedAt(),
                    post.getUpdatedAt()
            ))
            .collect(Collectors.toList());
    }
    
    public PostCreateResponse createPost(PostCreateRequest request) {
        Member author = memberRepository.findByUsername(request.username())
                .orElseThrow(() -> new NotFoundUserException(request.username()));

        Post savedPost = Post.create(request.title(), request.content(), author);

        return new PostCreateResponse(
                savedPost.getTitle(),
                savedPost.getContent(),
                author.getUsername(),
                savedPost.getCreatedAt());
    }

    public PostUpdateResponse updatePost(PostUpdateRequest request) {
        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new NotFoundPostException(request.postId()));

        post.update(request.title(), request.content());
        Post updatedPost = postRepository.save(post);

        return new PostUpdateResponse(
                updatedPost.getTitle(),
                updatedPost.getContent(),
                updatedPost.getAuthor().getUsername(),
                updatedPost.getUpdatedAt()
        );
    }
    
    public void deletePost(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException(postId));

        postRepository.deleteById(postId);
    }
}