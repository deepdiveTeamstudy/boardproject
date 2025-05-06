package com.practice.boardproject.post.domain.repository;

import com.practice.boardproject.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
