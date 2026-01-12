package com.blackwhitemap.blackwhitemap_back.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@ConditionalOnProperty(
        name = "spring.cache.type",
        havingValue = "caffeine",
        matchIfMissing = true
)
public class CacheConfig {

    /**
     * Caffeine 기반 CacheManager 설정
     * - 캐시 이름: "chefs"
     * - TTL: 5분
     * - 최대 크기: 10
     */
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(
                "chefs",
                "weeklyBestChefs",
                "dailyBestChefs",
                "chefClusters"
        );
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    /**
     * Caffeine 캐시 빌더
     * - expireAfterWrite: 마지막 쓰기 후 5분 경과 시 자동 만료
     * - maximumSize: 최대 10개 항목 저장
     */
    private Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(10)
                .recordStats();
    }
}
