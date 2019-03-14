package com.prf.newsagregator.security;

import lombok.Data;

@Data
public class JwtUser {
    private long id;
    private String username;
    private String role;
    private String password;
}
