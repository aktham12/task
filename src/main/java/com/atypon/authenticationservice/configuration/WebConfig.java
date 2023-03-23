package com.atypon.authenticationservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RateLimitingInterceptor()).addPathPatterns("/api/protected/endpoint1").addPathPatterns("/api/protected/endpoint2");
        registry.addInterceptor(new RateLimiterInterceptor());
    }

}