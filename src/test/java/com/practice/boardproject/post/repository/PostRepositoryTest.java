package com.practice.boardproject.post.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.practice.boardproject.member.domain.Member;
import com.practice.boardproject.post.domain.Post;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private EntityManager entityManager;

    private Member testMember;

    @BeforeEach
    void setUp() {
        testMember = Member.builder()
                .username("test-username")
                .password("test-password")
                .build();
        entityManager.persist(testMember);
    }

    @DisplayName("게시글을 수정할 수 있다")
    @Test
    void updatePost() {
        // given
        Post post = Post.builder()
                .title("원래 제목")
                .content("원래 내용")
                .author(testMember)
                .build();
        Post savedPost = postRepository.save(post);

        // when
        savedPost.update("수정된 제목", "수정된 내용");
        entityManager.flush();
        entityManager.clear();

        // then
        Post updatedPost = postRepository.findById(savedPost.getId()).get();
        assertThat(updatedPost.getTitle()).isEqualTo("수정된 제목");
        assertThat(updatedPost.getContent()).isEqualTo("수정된 내용");
    }

    @DisplayName("게시글을 삭제할 수 있다")
    @Test
    void deletePost() {
        // given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .author(testMember)
                .build();
        Post savedPost = postRepository.save(post);

        // when
        postRepository.deleteById(savedPost.getId());

        // then
        Optional<Post> result = postRepository.findById(savedPost.getId());
        assertThat(result).isEmpty();
    }

    @DisplayName("게시글을 등록하고 조회할 수 있다")
    @Test
    void createAndFindPost() {
        //given
        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .author(testMember)
                .build();
        Post savedPost = postRepository.save(post);
        entityManager.flush();
        entityManager.clear();

        // when
        Post findPost = postRepository.findById(savedPost.getId()).get();

        // then
        assertThat(findPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(findPost.getContent()).isEqualTo(post.getContent());
        assertThat(findPost.getAuthor().getId()).isEqualTo(testMember.getId());
    }

    @DisplayName("여러 게시글을 작성하고 모두 조회할 수 있다")
    @Test
    void findPosts() {
        //given
        Post post1 = Post.builder()
                .title("첫번째 제목")
                .content("첫번째 내용")
                .author(testMember)
                .build();

        Post post2 = Post.builder()
                .title("두번째 제목")
                .content("두번째 내용")
                .author(testMember)
                .build();

        Post post3 = Post.builder()
                .title("세번째 제목")
                .content("세번째 내용")
                .author(testMember)
                .build();

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        entityManager.flush();
        entityManager.clear();

        // when
        List<Post> posts = postRepository.findAll();

        // then
        assertThat(posts).hasSize(3);
        assertThat(posts).extracting("title")
                .containsExactlyInAnyOrder("첫번째 제목", "두번째 제목", "세번째 제목");
        assertThat(posts).extracting("content")
                .containsExactlyInAnyOrder("첫번째 내용", "두번째 내용", "세번째 내용");
    }

    @DisplayName("존재하지 않는 게시글 조회 시 Optional.empty가 반환된다")
    @Test
    void findNonExistingPost() {
        // when
        Optional<Post> result = postRepository.findById(999L);

        // then
        assertThat(result).isEmpty();
    }
}