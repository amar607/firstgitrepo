package com.amar.blog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;

    @NotEmpty(message = "name should not be empty")
    @Size(min=2, message = "name should have at least 2 characters")
    private String name;

    @NotEmpty(message = "description should not be empty")
    @Size(min=10, message = "description should have at least 10 characters")
    private String description;
}
