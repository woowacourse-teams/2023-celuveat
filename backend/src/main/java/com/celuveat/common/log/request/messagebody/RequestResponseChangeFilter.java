package com.celuveat.common.log.request.messagebody;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.util.ContentCachingResponseWrapper;

public class RequestResponseChangeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        ContentCachingResponseWrapper wrappingResponse
                = new ContentCachingResponseWrapper((HttpServletResponse) response);
        chain.doFilter(new ReadableRequestWrapper((HttpServletRequest) request), wrappingResponse);
        wrappingResponse.copyBodyToResponse();
    }
}
