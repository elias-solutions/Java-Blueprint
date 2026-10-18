package net.springboot_java_blueprint.ratelimiting;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RateLimitingFilter extends OncePerRequestFilter {
    private final FixedWindowRateLimiter rateLimiter;

    public RateLimitingFilter(FixedWindowRateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            if (rateLimiter.acquire()) {
                chain.doFilter(request, response);
            } else {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
        }
    }
}
