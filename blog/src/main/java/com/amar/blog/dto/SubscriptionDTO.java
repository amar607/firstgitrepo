package com.amar.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {

    private Long id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Email should not be null empty")
    @Email(message = "Wrong email format")
    private String email;
}
