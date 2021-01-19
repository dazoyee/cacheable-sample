package com.github.ioridazo.cacheable.sample.config;

import com.github.benmanes.caffeine.cache.Cache;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

// @see https://qiita.com/koya3jp/items/d632e95ebc57ee07695c
// 以下設定によって、 `@Cacheable` で指定したキャッシュに対して、
// 明示的に `application.yml` で指定しなくても、
// 動的にメトリクス登録をするための設定。
@EnableConfigurationProperties(CacheProperties.class)
@EnableCaching
@Configuration
public class CacheConfig {
    private final MeterRegistry meterRegistry;
    private final CacheProperties cacheProperties;

    public CacheConfig(MeterRegistry meterRegistry, CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
        this.meterRegistry = meterRegistry;
    }

    // CaffeineCacheConfigurationの実装をcopy
    @Bean
    public CaffeineCacheManager cacheManager() {
        CaffeineCacheManager cacheManager = createCacheManager();
        List<String> cacheNames = cacheProperties.getCacheNames();
        if (!CollectionUtils.isEmpty(cacheNames)) {
            cacheManager.setCacheNames(cacheNames);
        }
        return cacheManager;
    }
    // ここで独自CaffeineCacheManagerを生成
    private CaffeineCacheManager createCacheManager() {
        CaffeineCacheManager cacheManager = new InstrumentedCaffeineCacheManager(meterRegistry);
        setCacheBuilder(cacheManager);
        return cacheManager;
    }

    private void setCacheBuilder(CaffeineCacheManager cacheManager) {
        String specification = cacheProperties.getCaffeine().getSpec();
        if (StringUtils.hasText(specification)) {
            cacheManager.setCacheSpecification(specification);
        }
    }

    public static class InstrumentedCaffeineCacheManager extends CaffeineCacheManager {

        private final MeterRegistry meterRegistry;

        public InstrumentedCaffeineCacheManager(MeterRegistry meterRegistry) {
            this.meterRegistry = meterRegistry;
        }

        @Override
        protected Cache<Object, Object> createNativeCaffeineCache(String name) {
            Cache<Object, Object> nativeCache = super.createNativeCaffeineCache(name);
            CaffeineCacheMetrics.monitor(meterRegistry, nativeCache, name, Collections.emptyList());
            return nativeCache;
        }
    }
}
