package com.celuveat.celeb.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CelebCacheConfig {

    @Bean
    public CacheManager celebCacheManager() {
        return new ConcurrentMapCacheManager("all");
    }
}
