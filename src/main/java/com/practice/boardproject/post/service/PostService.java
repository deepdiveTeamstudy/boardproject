package com.practice.boardproject.post.service;

import com.practice.boardproject.member.domain.Member;
import com.practice.boardproject.member.repository.MemberRepository;
import com.practice.boardproject.post.domain.Post;
import com.practice.boardproject.post.dto.PostDTO;
import com.practice.boardproject.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    public void registPost(PostDTO newPost) throws IllegalAccessException {

        String title = newPost.getTitle();
        String content = newPost.getContent();
        String authorName = newPost.getUsername();

        // 작성자 조회
        Member author = memberRepository.findByUsername(authorName)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));


        Post createPost = Post.create(title, content, author);
        postRepository.save(createPost);
    }

}
