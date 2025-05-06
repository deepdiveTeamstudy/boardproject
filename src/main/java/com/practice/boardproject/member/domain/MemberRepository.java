package com.practice.boardproject.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // JpaRepository를 상속받아 기본적인 CRUD 기능을 제공
    Optional<Member> findByUsername(String username);

//    Optional<Object> findByUsername(Member getusername);
}
