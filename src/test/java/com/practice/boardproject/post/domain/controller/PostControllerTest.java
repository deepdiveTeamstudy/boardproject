package com.practice.boardproject.post.domain.controller;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.boardproject.global.exception.ErrorCode;
import com.practice.boardproject.member.domain.Member;
import com.practice.boardproject.member.repository.MemberRepository;
import com.practice.boardproject.post.domain.Post;
import com.practice.boardproject.post.domain.dto.request.PostCreateRequest;
import com.practice.boardproject.post.domain.dto.request.PostUpdateRequest;
import com.practice.boardproject.post.domain.repository.PostRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    private Member testMember;
    private Post testPost;

    @BeforeEach
    void setUp() {
        testMember = memberRepository.save(
                Member.builder()
                        .username("test-username")
                        .password("test-password")
                        .build()
        );

        testPost = postRepository.save(
                Post.builder()
                        .title("test-title")
                        .content("test-content")
                        .author(testMember)
                        .build()
        );
    }

    @DisplayName("[GET](/api/posts/{postId}) - 성공")
    @Test
    void getPostSuccess() throws Exception {
        Long postId = testPost.getId();

        mockMvc.perform(get("/api/posts/" + postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").value("test-title"))
                .andExpect(jsonPath("$.content").value("test-content"))
                .andExpect(jsonPath("$.author").value("test-username"))
                .andReturn()
                .getResponse();
    }

    @DisplayName("[GET](/api/posts/{postId}) - 실패 (존재하지 않는 게시물)")
    @Test
    void getPostNotFound() throws Exception {
        mockMvc.perform(get("/api/posts/" + 9999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ErrorCode.POST_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.POST_NOT_FOUND.getMessage()))
                .andReturn()
                .getResponse();
    }

    @DisplayName("[GET](/api/posts) - 성공")
    @Test
    void getPostsSuccess() throws Exception {
        testPost = postRepository.save(
                Post.builder()
                        .title("test-title1")
                        .content("test-content1")
                        .author(testMember)
                        .build()
        );

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts").isArray())
                .andExpect(jsonPath("$.posts[0].title").value("test-title1"))
                .andExpect(jsonPath("$.posts[0].content").value("test-content1"))
                .andExpect(jsonPath("$.posts[0].author").value("test-username"))
                .andExpect(jsonPath("$.posts[1].title").value("test-title"))
                .andExpect(jsonPath("$.posts[1].content").value("test-content"))
                .andExpect(jsonPath("$.posts[1].author").value("test-username"))
                .andExpect(jsonPath("$.size").value(2))
                .andReturn()
                .getResponse();
    }

    @DisplayName("[POST](/api/posts/new) - 성공")
    @Test
    void createPostSuccess() throws Exception {
        PostCreateRequest request = new PostCreateRequest(
                "test-username",
                "new-title",
                "new-content"
        );

        mockMvc.perform(post("/api/posts/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("new-title"))
                .andExpect(jsonPath("$.content").value("new-content"))
                .andExpect(jsonPath("$.author").value("test-username"))
                .andReturn()
                .getResponse();

        // DB 검증
        List<Post> posts = postRepository.findAll();
        Post savedPost = posts.stream()
                .filter(post -> post.getTitle().equals("new-title"))
                .findFirst()
                .orElseThrow();

        assertThat(savedPost.getTitle()).isEqualTo("new-title");
        assertThat(savedPost.getContent()).isEqualTo("new-content");
        assertThat(savedPost.getAuthor().getUsername()).isEqualTo("test-username");
    }

    @DisplayName("[POST](/api/posts/new) - 실패 (유효하지 않은 요청)")
    @Test
    void createPostInvalidRequest() throws Exception {
        PostCreateRequest request = new PostCreateRequest(
                "",
                "new-title",
                "new-content"
        );

        mockMvc.perform(post("/api/posts/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.INVALID_INPUT.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_INPUT.getMessage()))
                .andReturn()
                .getResponse();
    }

    @DisplayName("[POST](/api/posts/new) - 실패 (존재하지 않는 사용자)")
    @Test
    void createPostNonExistentUser() throws Exception {
        PostCreateRequest request = new PostCreateRequest(
                "none",
                "new-title",
                "new-content"
        );

        mockMvc.perform(post("/api/posts/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ErrorCode.USER_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_NOT_FOUND.getMessage()))
                .andReturn()
                .getResponse();
    }

    @DisplayName("[PUT](/api/posts) - 성공")
    @Test
    void updatePostSuccess() throws Exception {
        Long postId = testPost.getId();
        PostUpdateRequest request = new PostUpdateRequest(
                postId,
                "updated-title",
                "updated-content"
        );

        mockMvc.perform(put("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("updated-title"))
                .andExpect(jsonPath("$.content").value("updated-content"))
                .andExpect(jsonPath("$.author").value("test-username"))
                .andReturn()
                .getResponse();

        // DB 검증
        Post updatedPost = postRepository.findById(postId).orElseThrow();
        assertThat(updatedPost.getTitle()).isEqualTo("updated-title");
        assertThat(updatedPost.getContent()).isEqualTo("updated-content");
    }

    @DisplayName("[PUT](/api/posts) - 실패 (존재하지 않는 게시물)")
    @Test
    void updatePostNonExistent() throws Exception {
        Long nonExistentPostId = 9999L;
        PostUpdateRequest request = new PostUpdateRequest(
                nonExistentPostId,
                "updated-title",
                "updated-content"
        );

        mockMvc.perform(put("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ErrorCode.POST_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.POST_NOT_FOUND.getMessage()))
                .andReturn()
                .getResponse();
    }

    @DisplayName("[PUT](/api/posts) - 실패 (유효하지 않은 요청)")
    @Test
    void updatePostInvalidRequest() throws Exception {
        Long postId = testPost.getId();
        PostUpdateRequest request = new PostUpdateRequest(
                postId,
                "",  // 유효하지 않은 title (빈 문자열)
                "updated-content"
        );

        mockMvc.perform(put("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
    }

    @DisplayName("[DELETE](/api/posts/{postId}) - 성공")
    @Test
    void deletePostSuccess() throws Exception {
        Long postId = testPost.getId();

        mockMvc.perform(delete("/api/posts/" + postId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertThat(postRepository.findById(postId).isPresent()).isFalse();
    }

    @DisplayName("[DELETE](/api/posts/{postId}) - 실패 (존재하지 않는 게시물)")
    @Test
    void deletePostNonExistent() throws Exception {
        mockMvc.perform(delete("/api/posts/" + 9999L))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();
    }
}