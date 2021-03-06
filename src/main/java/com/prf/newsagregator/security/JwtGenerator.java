package com.prf.newsagregator.security;

import com.prf.newsagregator.entities.User;
import com.prf.newsagregator.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtGenerator {

    @Value("${secret}")
    private String SECRET;
    
    @Value("${validity}")
    private int VALIDITY;

    @Autowired
    UserRepository userRepository;
    
    public String generate(JwtUser jwtUser) {
    
        User dbUser = userRepository.findByUsername(jwtUser.getUsername());
        if (dbUser == null || !dbUser.isActive() || !dbUser.getPassword().equals(jwtUser.getPassword())) {
            throw new UsernameNotFoundException("Username not found or account is disabled");
        }
            
        Claims claims = Jwts.claims()
                .setSubject(jwtUser.getUsername());
        
        claims.put("userId", String.valueOf(jwtUser.getId()));
        claims.put("role", jwtUser.getRole());
        claims.put("password", jwtUser.getPassword());
    
        Date expireDate = getExpirationDate();
        
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();        
    }
    
    private Date getExpirationDate() {
        Date expireDate = new Date();
        expireDate.setTime(expireDate.getTime() + VALIDITY);        
    
        return expireDate;
    }
}
