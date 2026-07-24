package com.amar.blog.service;

import com.amar.blog.dto.CommentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    CommentDTO createComment(Long postId, CommentDTO commentDTO);

    List<CommentDTO> getCommentsByPostId(Long postId);

    CommentDTO getCommentsById(Long postId, Long commendId);

    CommentDTO updateComment(Long postId, Long commendId, CommentDTO commentDTO);

    void deleteComment(Long postId, Long commendId);

}
