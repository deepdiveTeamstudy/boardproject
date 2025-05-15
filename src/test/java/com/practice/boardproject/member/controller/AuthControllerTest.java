package com.practice.boardproject.member.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.boardproject.member.domain.Member;
import com.practice.boardproject.member.dto.request.LoginRequest;
import com.practice.boardproject.member.dto.request.SignUpRequest;
import com.practice.boardproject.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private Member testMember;

    @BeforeEach
    void setUp() {
        testMember = memberRepository.save(
                Member.builder()
                        .username("testUser")
                        .password(passwordEncoder.encode("1234"))
                        .build()
        );
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccess() throws Exception {
        LoginRequest loginRequest = new LoginRequest("testUser", "1234");

        mockMvc.perform(post("/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grantType").value("bearer"))
                .andExpect(jsonPath("$.memberName").value("testUser"))
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.accessTokenExpiresIn").exists());
    }

    @Test
    @DisplayName("회원이 존재하지 않는 경우 로그인 실패 테스트")
    void loginFailUserNotFound() throws Exception {
        LoginRequest loginRequest = new LoginRequest("none", "1234");

        mockMvc.perform(post("/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("비밀번호가 틀린 경우 로그인 실패 테스트")
    void loginFailWrongPassword() throws Exception {
        LoginRequest loginRequest = new LoginRequest("testUser", "wrong");

        mockMvc.perform(post("/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signUpSuccess() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("test", "1234");

        mockMvc.perform(post("/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk());

        Member savedMember = memberRepository.findByUsername("test").orElseThrow();
        assertThat(savedMember.getUsername()).isEqualTo("test");
        assertThat(passwordEncoder.matches("1234", savedMember.getPassword())).isTrue();
    }

    @Test
    @DisplayName("이미 존재하는 회원이름으로 가입시 회원 가입 실패 테스트")
    void signUpFail() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("testUser", "1234");

        mockMvc.perform(post("/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isConflict());
    }
}
