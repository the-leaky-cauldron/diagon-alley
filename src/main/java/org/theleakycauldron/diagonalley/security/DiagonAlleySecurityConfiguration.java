package org.theleakycauldron.diagonalley.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.theleakycauldron.diagonalley.security.filters.DiagonAlleyJwtAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class DiagonAlleySecurityConfiguration {

    private final DiagonAlleyJwtAuthenticationFilter diagonAlleyJwtAuthenticationFilter;

    public DiagonAlleySecurityConfiguration(
        DiagonAlleyJwtAuthenticationFilter diagonAlleyJwtAuthenticationFilter
        ) {

        this.diagonAlleyJwtAuthenticationFilter = diagonAlleyJwtAuthenticationFilter;

    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable())
                .sessionManagement(sessionManagement -> {
                    sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterBefore(diagonAlleyJwtAuthenticationFilter, SecurityContextHolderFilter.class)
            .build();
    }
}
