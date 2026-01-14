package com.blackwhitemap.blackwhitemap_back.config;

import com.github.benmanes.caffeine.cache.Cache;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass({MeterRegistry.class, CaffeineCache.class})
public class CacheMetricsConfig {

    private final CacheManager cacheManager;
    private final MeterRegistry meterRegistry;

    @PostConstruct
    public void bindCacheMetrics() {
        cacheManager.getCacheNames().forEach(cacheName -> {
            org.springframework.cache.Cache springCache = cacheManager.getCache(cacheName);
            if (springCache == null) {
                return;
            }

            Object nativeCache = springCache.getNativeCache();

            if (nativeCache instanceof Cache<?, ?> caffeineCache) {
                CaffeineCacheMetrics.monitor(
                        meterRegistry,
                        caffeineCache,
                        cacheName
                );
                log.info("Registered cache metrics for: {}", cacheName);
            }
        });
    }
}
