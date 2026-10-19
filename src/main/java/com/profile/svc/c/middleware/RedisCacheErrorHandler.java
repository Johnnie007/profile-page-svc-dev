package com.profile.svc.c.middleware;

import io.lettuce.core.RedisCommandExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.RedisSystemException;

@Slf4j
public class RedisCacheErrorHandler implements CacheErrorHandler {

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        handleRedisCacheError(exception, cache, "read");
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        handleRedisCacheError(exception, cache, "write");
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        handleRedisCacheError(exception, cache, "evict");
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        handleRedisCacheError(exception, cache, "clear");
    }

    private void handleRedisCacheError(RuntimeException exception, Cache cache, String operation) {
        if (isRedisOperationalFailure(exception)) {
            log.warn("Redis cache {} failed during {}; continuing without cache",
                    cache.getName(), operation, exception);
            return;
        }
        throw exception;
    }

    private boolean isRedisOperationalFailure(Throwable exception) {
        Throwable current = exception;
        while (current != null) {
            if (current instanceof RedisConnectionFailureException
                    || current instanceof RedisSystemException
                    || current instanceof RedisCommandExecutionException) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }
}
