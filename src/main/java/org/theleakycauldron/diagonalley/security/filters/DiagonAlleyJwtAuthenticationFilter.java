package org.theleakycauldron.diagonalley.security.filters;

import java.io.IOException;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class DiagonAlleyJwtAuthenticationFilter extends OncePerRequestFilter{

    @Value("${jwt.secret}")
    private String jwtSecret;

    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        String jwt = request.getHeader("Authorization");

        System.out.println(jwt);

        jwt = jwt.replace("Bearer ", "");

        if(jwt == null || jwt.isEmpty()){
            throw new RuntimeException("JWT is missing");
        }

       Jws<Claims> claims = Jwts.parser()
                                .verifyWith(getSecretKey())
                                .build()
                                .parseSignedClaims(jwt);
        
        if(claims.getPayload().getExpiration().getTime() < System.currentTimeMillis()){
            // Add JWT expiration exception
            throw new RuntimeException("JWT has expired");
        }
        
        
        filterChain.doFilter(request, response);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
    
}
