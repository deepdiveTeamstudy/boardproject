package com.practice.boardproject.post.repository;

import com.practice.boardproject.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // JpaRepository를 상속받아 기본적인 CRUD 기능을 제공
}
