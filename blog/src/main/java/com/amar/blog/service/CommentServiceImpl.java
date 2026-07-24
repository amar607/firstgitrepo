package com.amar.blog.service;

import com.amar.blog.dto.CommentDTO;
import com.amar.blog.entity.Comment;
import com.amar.blog.entity.Post;
import com.amar.blog.exceptions.BlogAPIException;
import com.amar.blog.exceptions.ResourceNotFoundException;
import com.amar.blog.repository.CommentRepository;
import com.amar.blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentServiceImpl implements CommentService{

    private PostRepository postRepository;
    private CommentRepository commentRepository;

    private ModelMapper mapper;

    @Autowired
    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.mapper = modelMapper;
    }

    @Override
    public CommentDTO createComment(Long postId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "id", postId));

        //mapToEntity using modelmapper
        Comment comment = mapper.map(commentDTO, Comment.class);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return mapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapper.map(comment, CommentDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentsById(Long postId, Long commendId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentRepository.findById(commendId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commendId));

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }
        return mapper.map(comment, CommentDTO.class);
    }

    @Override
    public CommentDTO updateComment(Long postId, Long commendId, CommentDTO commentDTO) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentRepository.findById(commendId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commendId));

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }

        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapper.map(updatedComment, CommentDTO.class);
    }

    @Override
    public void deleteComment(Long postId, Long commendId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentRepository.findById(commendId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commendId));

        if(!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
        }

        commentRepository.delete(comment);
    }
/* commenting to use modelmapper here
    private Comment mapToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());
        return comment;
    }

    private CommentDTO maptoDTO(Comment comment) {
        CommentDTO commentDto = new CommentDTO();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        //commentDto.setPost(comment.getPost());
        return commentDto;
    }*/
}
