package com.amar.blog.repository;

import com.amar.blog.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface PostRepository extends JpaRepository<Post, Long> {


    List<Post> findByCategoryId(Long categoryId);
}
