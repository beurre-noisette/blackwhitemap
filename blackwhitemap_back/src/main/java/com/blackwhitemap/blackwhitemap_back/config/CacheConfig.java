package com.blackwhitemap.blackwhitemap_back.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Caffeine 캐시 설정
 * - 캐시별 개별 TTL 및 maximumSize 적용
 * - recordStats()로 메트릭 수집 활성화 (CacheMetricsConfig에서 Micrometer 바인딩)
 */
@Configuration
@EnableCaching
@ConditionalOnProperty(
        name = "spring.cache.type",
        havingValue = "caffeine",
        matchIfMissing = true
)
public class CacheConfig {

    /**
     * 캐시별 개별 설정이 적용된 CacheManager
     * - chefs, chefClusters: 변경 빈도 낮음 → TTL 30분
     * - dailyBestChefs, weeklyBestChefs: 랭킹 갱신 주기 고려 → TTL 10분
     */
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        cacheManager.setCaches(List.of(
                buildCache(
                        "chefs",
                        30,
                        TimeUnit.MINUTES,
                        10
                ),
                buildCache(
                        "chefClusters",
                        30,
                        TimeUnit.MINUTES,
                        5
                ),
                buildCache(
                        "dailyBestChefs",
                        10,
                        TimeUnit.MINUTES,
                        5
                ),
                buildCache(
                        "weeklyBestChefs",
                        10,
                        TimeUnit.MINUTES,
                        10
                )
        ));

        return cacheManager;
    }

    private CaffeineCache buildCache(
            String name,
            long duration,
            TimeUnit unit,
            int maxSize
    ) {
        return new CaffeineCache(name,
                Caffeine.newBuilder()
                        .expireAfterWrite(duration, unit)
                        .maximumSize(maxSize)
                        .recordStats()
                        .build()
        );
    }
}
