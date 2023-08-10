package com.celuveat.common.config;

import com.celuveat.common.log.request.RequestLogInterceptor;
import com.celuveat.common.log.request.messagebody.RequestResponseChangeFilter;
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
    public FilterRegistrationBean<RequestResponseChangeFilter> requestChangeFilter() {
        FilterRegistrationBean<RequestResponseChangeFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestResponseChangeFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
