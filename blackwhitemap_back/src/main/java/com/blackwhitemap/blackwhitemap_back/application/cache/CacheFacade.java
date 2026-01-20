package com.blackwhitemap.blackwhitemap_back.application.cache;

import com.blackwhitemap.blackwhitemap_back.support.error.CoreException;
import com.blackwhitemap.blackwhitemap_back.support.error.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheFacade {
    private final CacheManager cacheManager;

    public void evictAllCaches() {
        cacheManager.getCacheNames().forEach(this::evictCache);

        log.info("모든 캐시 eviction 완료");
    }

    public void evictCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);

        if (cache == null) {
            throw new CoreException(ErrorType.NOT_FOUND, "존재하지 않는 캐시: " + cacheName);
        }

        cache.clear();

        log.info("캐시 eviction 완료: {}", cacheName);
    }
}
