package com.amar.blog.controller;

import com.amar.blog.dto.JwtAuthResponseDTO;
import com.amar.blog.dto.LoginDTO;
import com.amar.blog.dto.RegisterDTO;
import com.amar.blog.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationResource {

    private AuthenticationService authenticationService;

    public AuthenticationResource(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        String loginToken = authenticationService.login(loginDTO);

        JwtAuthResponseDTO jwtAuthResponseDTO = new JwtAuthResponseDTO();
        jwtAuthResponseDTO.setAccessToken(loginToken);
        return new ResponseEntity<>(jwtAuthResponseDTO, HttpStatus.OK);
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        String response = authenticationService.register(registerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
