package com.employeetmd.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Value("${cors.allowed-origin-patterns:http://localhost:3000,https://*.vercel.app}")
    private String allowedOriginPatterns;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String[] patterns = Arrays.stream(allowedOriginPatterns.split(","))
                        .map(String::trim)
                        .filter(pattern -> !pattern.isEmpty())
                        .toArray(String[]::new);

                registry.addMapping("/api/**")
                        .allowedOriginPatterns(patterns)
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
