package com.profile.svc.c.dc;

import org.junit.jupiter.api.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.RedisSystemException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CacheConfigTest {

    private final Cache cache = new ConcurrentMapCache("client-details-v2");
    private final CacheErrorHandler errorHandler = new CacheConfig().errorHandler();

    @Test
    void ignoresRedisConnectionFailuresSoTheRequestCanFallBackToTheDatabase() {
        assertDoesNotThrow(() -> errorHandler.handleCacheGetError(
                new RedisConnectionFailureException("Redis unavailable"), cache, "profile"));
    }

    @Test
    void ignoresRedisCommandFailuresSuchAsQuotaErrors() {
        assertDoesNotThrow(() -> errorHandler.handleCachePutError(
                new RedisSystemException("Redis command rejected", null), cache, "profile", "value"));
    }

    @Test
    void propagatesNonRedisCacheFailures() {
        IllegalArgumentException failure = new IllegalArgumentException("Invalid cache serialization");

        assertThrows(IllegalArgumentException.class,
                () -> errorHandler.handleCacheGetError(failure, cache, "profile"));
    }
}
