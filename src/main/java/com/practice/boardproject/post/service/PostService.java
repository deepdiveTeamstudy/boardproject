package com.practice.boardproject.post.service;

import com.practice.boardproject.member.domain.Member;
import com.practice.boardproject.member.domain.MemberRepository;
import com.practice.boardproject.post.domain.Post;
import com.practice.boardproject.post.dto.PostRequestDTO;
import com.practice.boardproject.post.dto.PostResponseDTO;
import com.practice.boardproject.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }


    public PostResponseDTO create(PostRequestDTO requestDTO) {
        Member author = (Member) memberRepository.findByUsername(requestDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("작성자 없음"));

        Post post = Post.builder()
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .author(author)
                .build();

        Post saved = postRepository.save(post);

        return PostResponseDTO.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .content(saved.getContent())
                .authorName(saved.getAuthor().getUsername())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    public List<PostResponseDTO> getAll() {
        return postRepository.findAll().stream()
                .map(post -> PostResponseDTO.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .authorName(post.getAuthor().getUsername())
                        .createdAt(post.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public PostResponseDTO getById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return PostResponseDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorName(post.getAuthor().getUsername())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public PostResponseDTO update(Long id, PostRequestDTO requestDTO) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        Member author = (Member) memberRepository.findByUsername(requestDTO.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("작성자 없음"));

        post = Post.builder()
                .id(post.getId())
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .author(author)
                .createdAt(post.getCreatedAt())
                .build();

        Post updated = postRepository.save(post);

        return PostResponseDTO.builder()
                .id(updated.getId())
                .title(updated.getTitle())
                .content(updated.getContent())
                .authorName(updated.getAuthor().getUsername())
                .createdAt(updated.getCreatedAt())
                .build();
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
