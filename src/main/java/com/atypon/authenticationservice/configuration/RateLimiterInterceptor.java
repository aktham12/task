package com.atypon.authenticationservice.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimiterInterceptor implements HandlerInterceptor {

    private static final long MAX_REQUESTS = 500;

    private static final long BLOCK_DURATION_MINUTES = 30*60*1000;
    private final Map<String, Long> requestCounts = new ConcurrentHashMap<>();
    private final Map<String, Long> requestTimes = new ConcurrentHashMap<>();



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = request.getRemoteAddr();
        Long count = requestCounts.get(ipAddress);

        if (count == null) {
            count = 0L;
        }
        count++;
        requestCounts.put(ipAddress, count);

        Long time = requestTimes.get(ipAddress);
        if (time == null) {
            time = System.currentTimeMillis();
        }

        long elapsedTime = System.currentTimeMillis() - time;
        if (elapsedTime > BLOCK_DURATION_MINUTES) {
            requestCounts.remove(ipAddress);
            requestTimes.remove(ipAddress);
            return true;
        }

        if (count > MAX_REQUESTS) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many requests");
            return false;
        }

        return true;
    }
}