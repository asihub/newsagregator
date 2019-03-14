package com.prf.newsagregator.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, API_USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
