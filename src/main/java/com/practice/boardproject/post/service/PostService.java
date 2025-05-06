package com.practice.boardproject.post.service;

import com.practice.boardproject.global.exception.CustomException;
import com.practice.boardproject.global.exception.ErrorCode;
import com.practice.boardproject.member.domain.Member;
import com.practice.boardproject.member.repository.MemberRepository;
import com.practice.boardproject.post.domain.Post;
import com.practice.boardproject.post.dto.PostDTO;
import com.practice.boardproject.post.dto.PostDetailDTO;
import com.practice.boardproject.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public PostService(PostRepository postRepository, MemberRepository memberRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.modelMapper = modelMapper;
    }

    // 게시글 등록
    @Transactional
    public void registPost(PostDTO newPost) throws CustomException {

        String title = newPost.getTitle();
        String content = newPost.getContent();
        String authorName = newPost.getUsername();

        // 작성자 조회
        Member author = memberRepository.findByUsername(authorName)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));


        Post createPost = Post.create(title, content, author);
        postRepository.save(createPost);
    }

    // 게시글 삭제
    @Transactional
    public void removePost(int postNo) throws CustomException {

        Post foundPost = postRepository.findById((long) postNo)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        postRepository.delete(foundPost);
    }

    // 게시글 수정
    @Transactional
    public void modifyPost(int postNo, PostDTO modifyPostInfo) throws CustomException {

        // 게시글 조회
        Post foundPost = postRepository.findById((long) postNo)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        foundPost.update(
                modifyPostInfo.getTitle(),
                modifyPostInfo.getContent()
        );
    }

    // 게시글 상세 조회
    public PostDetailDTO getPostDetail(int postNo) throws CustomException {

        // 게시글 조회
        Post foundPost = postRepository.findById((long) postNo)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        return modelMapper.map(foundPost, PostDetailDTO.class);
    }


    // 게시글 전체 조회 with 페이징
    public Page<PostDetailDTO> getAllPosts(Pageable pageable) {

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                Sort.by("createdAt").descending()
                );

        Page<Post> postList = postRepository.findAll(pageable);

        return postList.map(post -> modelMapper.map(post, PostDetailDTO.class));
    }
}
