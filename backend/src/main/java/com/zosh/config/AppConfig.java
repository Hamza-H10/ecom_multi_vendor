package com.zosh.config;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // Allow all OPTIONS requests for preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        // Secure API routes
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/api/products/*/reviews").permitAll()
                        .anyRequest().permitAll())
                // Add custom JWT validation filter
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Register CORS configuration

        return http.build();
    }

    // CORS Configuration Bean
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allowed origins
        configuration.setAllowedOrigins(Arrays.asList(
                "https://zosh-bazzar-zosh.vercel.app",
                "http://localhost:3000",
                "https://ecom-multi-vendor-97r14hrf8-hamza-hs-projects.vercel.app",
                "https://ecom-multi-vendor.vercel.app"));
        // Allowed HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Allow all headers
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        // Expose headers to clients
        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
        // Allow credentials for cookies or other auth mechanisms
        configuration.setAllowCredentials(true);
        // Cache preflight request results
        configuration.setMaxAge(3600L);

        // Register configuration for all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
