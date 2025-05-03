package com.practice.boardproject.domain.member.repository;

import com.practice.boardproject.domain.member.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  boolean existsMemberByUsername(String username);
}
