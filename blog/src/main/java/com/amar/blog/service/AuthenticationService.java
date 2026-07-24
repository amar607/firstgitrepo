package com.amar.blog.service;

import com.amar.blog.dto.LoginDTO;
import com.amar.blog.dto.RegisterDTO;

public interface AuthenticationService {
    String login(LoginDTO loginDTO);
    String register(RegisterDTO registerDTO);
}
