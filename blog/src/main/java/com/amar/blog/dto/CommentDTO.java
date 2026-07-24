package com.amar.blog.dto;

import com.amar.blog.entity.Post;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    private String email;

    @NotEmpty(message = "Body should not be empty")
    @Size(min=10, message = "Comment body should have at least 10 characters")
    private String body;
}
