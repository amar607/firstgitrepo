package com.amar.blog.dto;

import com.amar.blog.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Schema(
        description = "PostDTO Model Information"
)
public class PostDTO {


    private Long id;

    @Schema(
            description = "Blog Post title"
    )
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @Schema(
            description = "Blog Post author"
    )
    private String author;

    @Schema(
            description = "Blog Post content"
    )
    @NotEmpty
    @Size(min = 10, message = "Post content should have at least 10 characters")
    private String content;
    private Integer obsolete;

    @Schema(
            description = "Blog Post comments"
    )
    private Set<CommentDTO> comments;

    @Schema(
            description = "Blog Post category"
    )
    private Long categoryId;

    @Schema(
            description = "Blog Post Created date"
    )
    private LocalDateTime createdDate;

    @Schema(
            description = "Blog Post Image"
    )
    private MultipartFile multipartFile;

    @Schema(
            description = "Blog Post Image URI"
    )
    private String image;

    @Schema(
            description = "Blog Post for code snippet"
    )
    private String codeSnippet;

    @Schema(
            description = "Blog Post category"
    )
    private Category category;
}
