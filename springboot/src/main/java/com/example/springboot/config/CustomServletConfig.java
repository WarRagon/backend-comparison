package com.example.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.springboot.util.LocalDateFormatter;

@Configuration
public class CustomServletConfig implements WebMvcConfigurer{
    @Override
    public void addFormatters(@NonNull FormatterRegistry registry) {
        registry.addFormatter(new LocalDateFormatter());
    }

    /*
    @Override    
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS")
        .maxAge(300)
        .allowedHeaders("Authorization", "Cache-Control", "Content-Type");
    }
     */
}