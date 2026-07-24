package com.amar.blog.service;

import com.amar.blog.dto.LoginDTO;
import com.amar.blog.dto.RegisterDTO;
import com.amar.blog.entity.Role;
import com.amar.blog.entity.User;
import com.amar.blog.exceptions.BlogAPIException;
import com.amar.blog.repository.RoleRepository;
import com.amar.blog.repository.UserRepository;
import com.amar.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        //"User logged in successfully"
        return token;
    }

    @Override
    public String register(RegisterDTO registerDTO) {
        //add check for username present in database
        if(userRepository.existsByUsername(registerDTO.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "username already exists.");
        }

        //add check for email present in database
        if(userRepository.existsByEmail(registerDTO.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "email already exists.");
        }

        User user = new User();
        user.setName(registerDTO.getName());
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);

        return "User registered successfully";
    }
}
