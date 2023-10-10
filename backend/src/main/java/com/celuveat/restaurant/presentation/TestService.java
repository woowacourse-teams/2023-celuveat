package com.celuveat.restaurant.presentation;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class TestService {

    @Cacheable(cacheManager = "restaurantCacheManager", value = "test")
    public String test(String name) {
        System.out.println(name);
        return name;
    }

    @CacheEvict(cacheManager = "restaurantCacheManager", value = "test")
    public void remoe(String name) {
        System.out.println("remove name " + name );
    }

    @CachePut(cacheManager = "restaurantCacheManager", value = "test", key = "#name")
    public String update(String name) {
        System.out.println("update name " + name);
        return "update" + name;
    }
}
