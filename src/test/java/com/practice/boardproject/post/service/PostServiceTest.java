package com.practice.boardproject.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.practice.boardproject.global.exception.ErrorCode;
import com.practice.boardproject.global.exception.GlobalException;
import com.practice.boardproject.member.domain.Member;
import com.practice.boardproject.member.repository.MemberRepository;
import com.practice.boardproject.post.domain.Post;
import com.practice.boardproject.post.dto.request.PostCreateRequest;
import com.practice.boardproject.post.dto.request.PostUpdateRequest;
import com.practice.boardproject.post.dto.response.PostCreateResponse;
import com.practice.boardproject.post.dto.response.PostDetailResponse;
import com.practice.boardproject.post.dto.response.PostListResponse;
import com.practice.boardproject.post.dto.response.PostUpdateResponse;
import com.practice.boardproject.post.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostService postService;

    private Member testMember;

    private Post testPost;

    @BeforeEach
    void setUp() {
        // 테스트 멤버 저장
        testMember = Member.builder().username("test-username").password("test-password").build();
        memberRepository.save(testMember);

        // 테스트 게시글 저장
        testPost = Post.builder().title("test-title").content("test-content").author(testMember).build();
        postRepository.save(testPost);
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("존재하는 게시글 ID로 게시글을 조회할 수 있다")
    @Test
    void getPostSuccess() {
        // when
        PostDetailResponse response = postService.getPost(testPost.getId());

        // then
        assertThat(response.title()).isEqualTo("test-title");
        assertThat(response.content()).isEqualTo("test-content");
        assertThat(response.author()).isEqualTo("test-username");
    }

    @DisplayName("존재하지 않는 게시글 ID로 조회 시 예외가 발생한다")
    @Test
    void getPostNotFound() {
        // when & then
        GlobalException exception = assertThrows(GlobalException.class, () -> {
            postService.getPost(999L);
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.POST_NOT_FOUND);
    }

    @DisplayName("전체 게시글 목록을 조회할 수 있다")
    @Test
    void getPostsSuccess() {
        // given
        Post anotherPost = Post.builder().title("다른 제목").content("다른 내용").author(testMember).build();
        postRepository.save(anotherPost);

        // when
        PostListResponse response = postService.getPosts();

        // then
        assertThat(response.size()).isEqualTo(2);
        assertThat(response.posts()).hasSize(2);

    }

    @DisplayName("작성자 정보로 게시글을 생성할 수 있다")
    @Test
    void createPostSuccess() {
        // given
        PostCreateRequest request = new PostCreateRequest("test-username", "new-title", "new-content");

        // when
        PostCreateResponse response = postService.createPost(request);

        // then
        assertThat(response.title()).isEqualTo("new-title");
        assertThat(response.content()).isEqualTo("new-content");
        assertThat(response.author()).isEqualTo("test-username");

        // 데이터베이스에 실제로 저장되었는지 확인
        assertThat(postRepository.findAll().size()).isEqualTo(2); // 기존 1개 + 새로 생성한 1개
    }

    @DisplayName("존재하지 않는 사용자로 게시글 생성 시 예외가 발생한다")
    @Test
    void createPostWithNonExistingUser() {
        // given
        PostCreateRequest request = new PostCreateRequest("none", "new-title", "new-content");

        // when & then
        GlobalException exception = assertThrows(GlobalException.class, () -> {
            postService.createPost(request);
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_FOUND);
    }

    @DisplayName("존재하는 게시글을 수정할 수 있다")
    @Test
    void updatePostSuccess() {
        // given
        PostUpdateRequest request = new PostUpdateRequest(testPost.getId(), "update-title", "update-content");

        // when
        PostUpdateResponse response = postService.updatePost(request);

        // then
        assertThat(response.title()).isEqualTo("update-title");
        assertThat(response.content()).isEqualTo("update-content");

        // 데이터 베이스 확인
        Post updatedPost = postRepository.findById(testPost.getId()).orElseThrow();
        assertThat(updatedPost.getTitle()).isEqualTo("update-title");
        assertThat(updatedPost.getContent()).isEqualTo("update-content");
    }

    @DisplayName("존재하지 않는 게시글 수정 시 예외가 발생한다")
    @Test
    void updateNonExistingPost() {
        // given
        PostUpdateRequest request = new PostUpdateRequest(999L, "update-title", "update-content");

        // when & then
        GlobalException exception = assertThrows(GlobalException.class, () -> {
            postService.updatePost(request);
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.POST_NOT_FOUND);
    }

    @DisplayName("존재하는 게시글을 삭제할 수 있다")
    @Test
    void deletePostSuccess() {
        // when
        postService.deletePost(testPost.getId());

        // then
        assertThat(postRepository.findById(testPost.getId())).isEmpty();
    }

    @DisplayName("존재하지 않는 게시글 삭제 시 예외가 발생한다")
    @Test
    void deleteNonExistingPost() {
        // when & then
        GlobalException exception = assertThrows(GlobalException.class, () -> {
            postService.deletePost(999L);
        });

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.POST_NOT_FOUND);
    }
}