package com.amar.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

public class PasswordEncoder {
    public static void main(String[] args) {
        org.springframework.security.crypto.password.PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("admin"));
        System.out.println(passwordEncoder.encode("client"));
    }
}
