package net.springboot_java_blueprint.ratelimiting;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RateLimitProperties.class)
public class RateLimitingConfiguration {
    private static final int BEFORE_SECURITY_FILTER_CHAIN = -101;

    @Bean
    public FilterRegistrationBean<RateLimitingFilter> rateLimitingFilter(RateLimitProperties properties) {
        var rateLimiter = new FixedWindowRateLimiter(
                properties.permitLimit(), properties.window(), properties.queueLimit(), System::nanoTime);

        var registration = new FilterRegistrationBean<>(new RateLimitingFilter(rateLimiter));
        registration.addUrlPatterns("/api/*");
        registration.setOrder(BEFORE_SECURITY_FILTER_CHAIN);
        return registration;
    }
}
