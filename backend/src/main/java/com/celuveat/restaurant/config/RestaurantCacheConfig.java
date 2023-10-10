package com.celuveat.restaurant.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestaurantCacheConfig {

    @Bean
    public CacheManager restaurantCacheManager() {
        return new ConcurrentMapCacheManager("latest", "region", "recommend");
    }
}
