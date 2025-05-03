package com.practice.boardproject.post.service;

import com.practice.boardproject.member.domain.Member;
import com.practice.boardproject.member.repository.MemberRepository;
import com.practice.boardproject.post.domain.Post;
import com.practice.boardproject.post.dto.PostDTO;
import com.practice.boardproject.post.dto.PostDetailDTO;
import com.practice.boardproject.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;


    @Autowired
    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    // 게시글 등록
    @Transactional
    public void registPost(PostDTO newPost) {

        String title = newPost.getTitle();
        String content = newPost.getContent();
        String authorName = newPost.getUsername();

        // 작성자 조회
        Member author = memberRepository.findByUsername(authorName)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));


        Post createPost = Post.create(title, content, author);
        postRepository.save(createPost);
    }

    // 게시글 삭제
    @Transactional
    public void removePost(int postNo) {

        Post foundPost = postRepository.findById((long) postNo)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        postRepository.delete(foundPost);
    }

    // 게시글 수정
    @Transactional
    public void modifyPost(int postNo, PostDTO modifyPostInfo) {

        // 게시글 조회
        Post foundPost = postRepository.findById((long) postNo)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        foundPost.update(
                modifyPostInfo.getTitle(),
                modifyPostInfo.getContent()
        );
    }

    // 게시글 상세 조회
    public PostDetailDTO getPostDetail(int postNo) {

        // 게시글 조회
        Post foundPost = postRepository.findById((long) postNo)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        String title = foundPost.getTitle();
        String content = foundPost.getContent();
        String authorName = foundPost.getAuthor().getUsername();
        LocalDateTime createdAt = foundPost.getCreatedAt();
        LocalDateTime updatedAt = foundPost.getUpdatedAt();

        return new PostDetailDTO(title, content, authorName, createdAt, updatedAt);
    }
}
