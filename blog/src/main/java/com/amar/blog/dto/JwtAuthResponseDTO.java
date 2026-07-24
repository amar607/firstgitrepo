package com.amar.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtAuthResponseDTO {

    private String accessToken;
    private String tokenType="Bearer";
}
