package com.celuveat.common.util;

import static org.springframework.http.HttpMethod.OPTIONS;

import jakarta.servlet.http.HttpServletRequest;

public class CorsUtil {

    public static boolean isPreflightRequest(HttpServletRequest request) {
        return OPTIONS.matches(request.getMethod());
    }
}
