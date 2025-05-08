package com.practice.boardproject.member.service;

import com.practice.boardproject.member.domain.Member;

public interface MemberService {
    Member findMemberByUsername(String username);
}
