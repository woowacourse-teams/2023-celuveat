package com.celuveat.common.config;

import java.util.Collection;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@EnableCaching
@Configuration
public class ApplicationCacheConfig {

    /**
     * 여러개의 Cache 매니저를 사용하기 위해서는 @Primary 가 붙은 CacheManager 빈이 존재해야 함.
     * 이를 위한 CacheManager 등록
     */
    @Bean
    @Primary
    public CacheManager ignored() {
        return new CacheManager() {
            @Override
            public Cache getCache(String name) {
                throw new RuntimeException("캐시 설정이 잘못되었습니다.");
            }

            @Override
            public Collection<String> getCacheNames() {
                throw new RuntimeException("캐시 설정이 잘못되었습니다.");
            }
        };
    }
}
