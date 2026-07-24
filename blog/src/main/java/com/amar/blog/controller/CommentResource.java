package com.amar.blog.controller;

import com.amar.blog.dto.CommentDTO;
import com.amar.blog.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@Tag(
        name = "CRUD Rest APIs for Comment Resource"
)
public class CommentResource {

    private CommentService commentService;

    @Autowired
    public CommentResource(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping("client/post/{postId}/comment")
    public ResponseEntity createComment(@Valid @RequestBody CommentDTO request, @RequestParam Long postId) {

        return new ResponseEntity(commentService.createComment(postId, request),HttpStatus.CREATED);
    }

    @GetMapping("client/post/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable("postId") Long postId) {
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.OK);
    }


    @GetMapping("client/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getComments(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ) {
        CommentDTO commentDTO = commentService.getCommentsById(postId, commentId);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("admin/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(
            @Valid @RequestBody CommentDTO commentDTO,
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ){

        CommentDTO updatedComment = commentService.updateComment(postId, commentId, commentDTO);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("admin/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> updateComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ) {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }
}
