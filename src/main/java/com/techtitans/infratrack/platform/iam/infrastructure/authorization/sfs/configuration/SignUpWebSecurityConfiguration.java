package com.techtitans.infratrack.platform.iam.infrastructure.authorization.sfs.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * v0.2.0 — permite sign-up y Swagger sin JWT (JWT se agrega en v0.3.0 sign-in).
 */
@Configuration
@EnableWebSecurity
public class SignUpWebSecurityConfiguration {

    @Bean
    SecurityFilterChain signUpSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}
