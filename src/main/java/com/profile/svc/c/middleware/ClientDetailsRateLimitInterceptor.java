package com.profile.svc.c.middleware;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.profile.svc.c.security.RateLimitProperties;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;

@Component
public class ClientDetailsRateLimitInterceptor implements HandlerInterceptor {
    private final Cache<String, Bucket> buckets;
    private final RateLimitProperties properties;

    public ClientDetailsRateLimitInterceptor(RateLimitProperties properties) {
        this.properties = properties;
        this.buckets = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterAccess(Duration.ofHours(1))
                .build();
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        Bucket bucket = buckets.get(request.getRemoteAddr(), this::newBucket);
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            return true;
        }

        long retryAfterSeconds = Math.max(
                1,
                Duration.ofNanos(probe.getNanosToWaitForRefill()).toSeconds()
        );
        response.setHeader("Retry-After", Long.toString(retryAfterSeconds));
        response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), "Rate limit exceeded");
        return false;
    }

    private Bucket newBucket(String ignored) {
        return Bucket.builder()
                .addLimit(limit -> limit.capacity(properties.capacity())
                        .refillGreedy(properties.tokensPerMinute(), Duration.ofMinutes(1)))
                .build();
    }
}
