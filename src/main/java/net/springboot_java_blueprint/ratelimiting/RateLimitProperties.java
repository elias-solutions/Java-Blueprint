package net.springboot_java_blueprint.ratelimiting;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties("blueprint.rate-limit")
public record RateLimitProperties(int permitLimit, Duration window, int queueLimit) {
}
