package com.celuveat.auth.config;

import static org.springframework.http.HttpMethod.GET;

import com.celuveat.auth.presentation.AuthInterceptor.UriAndMethodsCondition;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthExcludeConfig {
    
    @Bean
    public Set<UriAndMethodsCondition> authNotRequiredConditions() {
        return Set.of(
                new UriAndMethodsCondition("/api/reviews", Set.of(GET))
        );
    }
}
