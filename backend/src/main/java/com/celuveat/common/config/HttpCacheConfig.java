package com.celuveat.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

@Configuration
public class HttpCacheConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
        webContentInterceptor.addCacheMapping(CacheControl.noCache().cachePrivate(), "/**");
        registry.addInterceptor(webContentInterceptor);
    }
}
