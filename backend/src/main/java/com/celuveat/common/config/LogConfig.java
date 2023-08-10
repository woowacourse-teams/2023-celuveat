package com.celuveat.common.config;

import com.celuveat.common.log.request.RequestChangeFilter;
import com.celuveat.common.log.request.RequestLogInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class LogConfig implements WebMvcConfigurer {

    private final RequestLogInterceptor requestLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestLogInterceptor)
                .addPathPatterns("/**");
    }

    @Bean
    public FilterRegistrationBean<RequestChangeFilter> requestChangeFilter() {
        FilterRegistrationBean<RequestChangeFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestChangeFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
