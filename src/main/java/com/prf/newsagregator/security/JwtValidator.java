package com.prf.newsagregator.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {
    
    @Value("${secret}")
    private String SECRET;
    
    @Value("${secret}")
    private String secret;
    
    public JwtUser validate(String token) {
        
        JwtUser jwtUser = null;
        
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
            
            jwtUser = new JwtUser();
            jwtUser.setUsername(body.getSubject());
            jwtUser.setPassword((String) body.get("password"));
            jwtUser.setId(Long.parseLong((String) body.get("userId")));
            jwtUser.setRole((String) body.get("role"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return jwtUser;
    }
}
