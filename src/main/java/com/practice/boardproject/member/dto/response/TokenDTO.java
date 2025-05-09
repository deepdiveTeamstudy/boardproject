package com.practice.boardproject.member.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class TokenDTO {

	private String grantType;			// 토큰 타입
	private String memberName; 			// 인증받은 회원 이름
	private String accessToken; 		// 액세스 토큰
	private Long accessTokenExpiresIn;	// Long 형의 만료 시간

}
