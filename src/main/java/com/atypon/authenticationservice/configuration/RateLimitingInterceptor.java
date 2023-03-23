package com.atypon.authenticationservice.configuration;

import io.github.bucket4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {

    private static final long MAX_REQUESTS_PER_MINUTE = 10;
    private static final long BLOCK_DURATION_MINUTES = 1;

    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket createNewBucket() {
        Refill refill = Refill.intervally(MAX_REQUESTS_PER_MINUTE, Duration.ofMinutes(BLOCK_DURATION_MINUTES));
        Bandwidth limit = Bandwidth.classic(MAX_REQUESTS_PER_MINUTE, refill);
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = request.getRemoteAddr();
        Bucket requestBucket = buckets.computeIfAbsent(ipAddress+request.getRequestURI(), key -> createNewBucket());

        if (requestBucket.tryConsume(1)) {
            return true;
        } else {
            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many requests");
            return false;
        }
    }
}