package org.theleakycauldron.diagonalley.security.filters;

import java.io.IOException;
import java.util.Collections;

import javax.crypto.SecretKey;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final ObjectMapper objectMapper;

    public DiagonAlleyJwtAuthenticationFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        String jwt = request.getHeader("Authorization");

        System.out.println(jwt);

        if((jwt == null || jwt.isEmpty()) && request.getAttribute("JWT_TOKEN") == null){

            System.out.println("JWT is missing");
            filterChain.doFilter(request, response);
            return;

        }

        // forwarded request
        else if(request.getAttribute("JWT_TOKEN") != null) {

            jwt = (String) request.getAttribute("JWT_TOKEN");

        }

        jwt = jwt.replace("Bearer ", "");

        request.setAttribute("JWT_TOKEN", jwt);

        Jws<Claims> claims = Jwts.parser()
                                .verifyWith(getSecretKey())
                                .build()
                                .parseSignedClaims(jwt);

        if(claims.getPayload().getExpiration().getTime() < System.currentTimeMillis()){
            // Add JWT expiration exception
            throw new RuntimeException("JWT has expired");
        }

        // Creating Authentication token and setting in SecurityContextHolder

        Object email = claims.getPayload().get("email");
        String emailString = objectMapper.writeValueAsString(email);
        emailString = emailString.replace("\"", "");

        Authentication authentication = new UsernamePasswordAuthenticationToken(emailString, null, Collections.emptyList());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
    
}
