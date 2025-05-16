package com.practice.boardproject.comment.service;

import com.practice.boardproject.comment.domain.Comment;
import com.practice.boardproject.comment.dto.request.CommentCreateRequest;
import com.practice.boardproject.comment.dto.request.CommentUpdateRequest;
import com.practice.boardproject.comment.dto.response.CommentCreateResponse;
import com.practice.boardproject.comment.dto.response.CommentUpdateResponse;
import com.practice.boardproject.comment.repository.CommentRepository;
import com.practice.boardproject.global.exception.ErrorCode;
import com.practice.boardproject.global.exception.GlobalException;
import com.practice.boardproject.member.domain.Member;
import com.practice.boardproject.member.repository.MemberRepository;
import com.practice.boardproject.post.domain.Post;
import com.practice.boardproject.post.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@WithMockUser(username = "test01") // test01 사용자로 인증
class CommentServiceTest {

    @Autowired // 왜 여기서는 @RequiredArgsConstructor 안쓰는거지?
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Post testPost;

    private Comment testComment;

    @BeforeEach
    void setUp() {
        // 테스트 멤버 저장
        Member testMember = Member.builder()
                .username("test01")
                .password("1234")
                .build();
        memberRepository.save(testMember);

        // 테스트 게시글 저장
        testPost = Post.builder()
                .title("test-title")
                .content("test-content")
                .author(testMember)
                .build();
        postRepository.save(testPost);

        // 테스트 댓글 저장
        testComment = Comment.builder()
                .content("test-comment")
                .author(testMember)
                .post(testPost)
                .build();
        commentRepository.save(testComment);
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAllInBatch();
        postRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("로그인된 사용자로 댓글 등록 성공")
    @Test
    void createCommentSuccess() {

        //given
        CommentCreateRequest request = new CommentCreateRequest("new-comment", testPost.getId());

        //when
        CommentCreateResponse response = commentService.createComment(request);

        //then
        assertThat(response.comment()).isEqualTo("new-comment");
        assertThat(response.author()).isEqualTo("test01");
    }

    @DisplayName("존재하지 않는 게시글에 댓글 생성 시 예외 발생")
    @Test
    void createCommentWithNonExistingPost() {

        //given
        CommentCreateRequest request = new CommentCreateRequest("new-comment", 999L);

        //when
        GlobalException exception = assertThrows(GlobalException.class, () -> {
            commentService.createComment(request);
        });

        //then
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.POST_NOT_FOUND);
    }

    @DisplayName("존재하는 댓글 수정 성공")
    @Test
    void updateCommentSuccess() {

        //given
        CommentUpdateRequest request = new CommentUpdateRequest(testComment.getId(),"update-comment");

        //when
        CommentUpdateResponse response = commentService.updateComment(request);

        //then
        assertThat(response.content()).isEqualTo("update-comment");
        assertThat(response.author()).isEqualTo("test01");
    }

    @DisplayName("존재하지 않는 댓글 수정 시 예외 발생")
    @Test
    void updateCommentWithNonExistingComment() {

        //given
        CommentUpdateRequest request = new CommentUpdateRequest(999L,"update-comment");

        //when
        GlobalException exception = assertThrows(GlobalException.class, () -> {
            commentService.updateComment(request);
        });

        //then
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.COMMENT_NOT_FOUND);
    }

    @DisplayName("댓글 작성자가 아닌 사용자가 댓글 수정 시 예외 발생")
    @Test
    @WithMockUser("test02")
    void updateCommentWithNonOwner() {

        //given
        CommentUpdateRequest request = new CommentUpdateRequest(testComment.getId(),"update-comment");

        //when
        GlobalException exception = assertThrows(GlobalException.class, () -> {
            commentService.updateComment(request);
        });

        //then
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    @DisplayName("존재하는 댓글 삭제 성공")
    @Test
    void deleteCommentSuccess() {

        //when
        commentService.deleteComment(testComment.getId());

        //then
        assertThat(commentRepository.findById(testComment.getId())).isEmpty();
    }

    @DisplayName("존재하지 않는 댓글 삭제 시 예외 발생")
    @Test
    void deleteCommentWithNonExistingComment() {

        //when
        GlobalException exception = assertThrows(GlobalException.class, () -> {
            commentService.deleteComment(999L);
        });

        //then
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.COMMENT_NOT_FOUND);
    }

    @DisplayName("댓글 작성자가 아닌 사용자가 댓글 삭제 시 예외 발생")
    @Test
    @WithMockUser("test02")
    void deleteCommentWithNonOwner() {

        //when
        GlobalException exception = assertThrows(GlobalException.class, () -> {
            commentService.deleteComment(testComment.getId());
        });

        //then
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.UNAUTHORIZED_ACCESS);
    }
}