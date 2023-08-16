package com.celuveat.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

@Configuration
public class PathConfig {

    @Bean
    public PathMatcher pathMatcher() {
        return new AntPathMatcher();
    }
}
