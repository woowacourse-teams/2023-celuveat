package com.celuveat.auth.config;

import static org.springframework.http.HttpMethod.GET;

import com.celuveat.auth.presentation.AuthArgumentResolver;
import com.celuveat.auth.presentation.AuthInterceptor;
import com.celuveat.auth.presentation.AuthInterceptor.UriAndMethodsCondition;
import com.celuveat.auth.presentation.LooseAuthArgumentResolver;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AuthConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final AuthArgumentResolver authArgumentResolver;
    private final LooseAuthArgumentResolver looseAuthArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(setUpAuthInterceptor())
                .addPathPatterns(
                        "/restaurants/**/like",
                        "/reviews/**",
                        "/members/**",
                        "/oauth/logout/**",
                        "/oauth/withdraw/**"
                ).order(2);
    }

    private AuthInterceptor setUpAuthInterceptor() {
        authInterceptor.setAuthNotRequiredConditions(
                new UriAndMethodsCondition("/api/reviews", Set.of(GET))
        );
        return authInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
        resolvers.add(looseAuthArgumentResolver);
    }
}
