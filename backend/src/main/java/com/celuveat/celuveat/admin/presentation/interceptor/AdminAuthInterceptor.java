package com.celuveat.celuveat.admin.presentation.interceptor;

import static com.celuveat.celuveat.admin.exception.AdminExceptionType.UNAUTHORIZED_ADMIN;
import static com.celuveat.celuveat.common.auth.AuthSession.ADMIN_AUTH_SESSION_KEY;

import com.celuveat.celuveat.admin.exception.AdminException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = getSession(request);
        Optional.ofNullable(session.getAttribute(ADMIN_AUTH_SESSION_KEY))
                .map(it -> (Long) it)
                .orElseThrow(() -> new AdminException(UNAUTHORIZED_ADMIN));
        return true;
    }

    private HttpSession getSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new AdminException(UNAUTHORIZED_ADMIN);
        }
        return session;
    }
}
