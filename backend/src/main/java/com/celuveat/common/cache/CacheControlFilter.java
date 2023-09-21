package com.celuveat.common.cache;

import static org.springframework.http.CacheControl.noCache;
import static org.springframework.http.HttpHeaders.CACHE_CONTROL;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/*")
public class CacheControlFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader(CACHE_CONTROL, noCache().cachePrivate().getHeaderValue());
    }
}
