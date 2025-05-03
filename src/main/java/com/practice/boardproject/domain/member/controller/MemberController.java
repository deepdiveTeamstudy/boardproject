package com.practice.boardproject.domain.member.controller;

import com.practice.boardproject.domain.member.dto.request.CreateMemberRequest;
import com.practice.boardproject.domain.member.service.CommandMemberService;
import com.practice.boardproject.global.api_response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "회원 관련 API")
public class MemberController {

  private final CommandMemberService commandMemberService;

  @PostMapping()
  @Operation(summary = "회원가입")
  public ApiResponse<Void> createMember(@RequestBody @Valid CreateMemberRequest request) {
    commandMemberService.createMember(request);
    return ApiResponse.ok();
  }

}
