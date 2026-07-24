package com.amar.blog.service;

import com.amar.blog.dto.PostDTO;
import com.amar.blog.dto.PostPaginationResponse;
import com.amar.blog.entity.Post;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface PostService {
    public List<PostDTO> getAllPosts();

    public PostPaginationResponse getAllPosts(String base_url, int pageNo, int pageSize, String sortBy, boolean desc);
    PostDTO createPost(PostDTO post);

    PostDTO updatePost(Long id, PostDTO post);

    PostDTO getPostById(String base_url, Long id);

    void deletePostById(Long id);

    List<PostDTO> getPostsByCategoryId(Long categoryId);
}
