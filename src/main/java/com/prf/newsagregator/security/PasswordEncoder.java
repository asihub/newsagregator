package com.prf.newsagregator.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    public static void main(String[] args) {
        final String DEFAULT_PASSWORD = "123";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode(DEFAULT_PASSWORD));
    }
}
