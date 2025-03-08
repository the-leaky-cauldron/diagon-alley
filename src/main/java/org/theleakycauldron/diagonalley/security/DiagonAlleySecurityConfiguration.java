package org.theleakycauldron.diagonalley.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> {
                    sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authorizeHttpRequests(
                        authorize -> {
                            authorize
//                                    .anyRequest().permitAll();
                                    .requestMatchers("/swagger-ui/**").permitAll()
                                    .requestMatchers("/swagger-ui.html").permitAll()
                                    .requestMatchers("/v3/api-docs/**").permitAll()
                                    .requestMatchers("/swagger-resources/**").permitAll()
                                    .requestMatchers("/webjars/**").permitAll()
                                    .requestMatchers("/payment/**").permitAll()
//                                    .requestMatchers("/error", "/error/**").permitAll()
                                    .anyRequest()
                                    .authenticated();

                        }

                )
                .addFilterAfter(diagonAlleyJwtAuthenticationFilter, SecurityContextHolderFilter.class)
            .build();
    }
}
