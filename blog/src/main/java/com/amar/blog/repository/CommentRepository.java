package com.amar.blog.repository;

import com.amar.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //No need to add @Repository because further extending SimpleJPARepository class already extending this annotation.

    List<Comment> findByPostId(Long postId);
}
