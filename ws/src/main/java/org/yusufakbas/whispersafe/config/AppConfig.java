package org.yusufakbas.whispersafe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers("/ws/**").permitAll() // Allows WebSocket connections
                        .requestMatchers("/api/v1/**").permitAll() // Allows certain API endpoints
                        .requestMatchers(("/**")).permitAll().anyRequest().authenticated() // Requires authentication for all other requests
                ).addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class) // Replace JwtValidator with your actual filter
                .csrf(AbstractHttpConfigurer::disable) // Disables CSRF protection
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Adds CORS configuration
                .formLogin(formLogin -> formLogin.defaultSuccessUrl("/home").permitAll()) // Configures form login settings
                .httpBasic(withDefaults -> {
                }); // Enables HTTP Basic authentication

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*")); // Allows all origins with wildcard
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Specifies allowed HTTP methods
        config.setAllowCredentials(true); // Allows credentials to be sent
        config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type")); // Specifies allowed HTTP headers
        config.setExposedHeaders(List.of("Authorization")); // Specifies exposed response headers
        config.setMaxAge(3600L); // Sets the validity period of the CORS configuration

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Applies CORS configuration to all endpoints
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}