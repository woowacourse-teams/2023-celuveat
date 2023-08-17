package com.celuveat.auth.presentation;

import static com.celuveat.auth.exception.AuthExceptionType.UNAUTHORIZED_REQUEST;
import static com.celuveat.common.auth.AuthConstant.JSESSION_ID;

import com.celuveat.auth.exception.AuthException;
import com.celuveat.common.util.CorsUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthContext authContext;
    private final ObjectProvider<PathMatcher> pathMatcherProvider;
    private final Set<UriAndMethodsCondition> authNotRequiredConditions = new HashSet<>();

    public void setAuthNotRequiredConditions(UriAndMethodsCondition... authNotRequiredConditions) {
        this.authNotRequiredConditions.clear();
        this.authNotRequiredConditions.addAll(Arrays.asList(authNotRequiredConditions));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (CorsUtil.isPreflightRequest(request)) {
            return true;
        }
        if (isAuthenticationNotRequired(request)) {
            return true;
        }
        HttpSession session = getSession(request);
        Long memberId = Optional.ofNullable(session.getAttribute(JSESSION_ID))
                .map(id -> (Long) id)
                .orElseThrow(() -> new AuthException(UNAUTHORIZED_REQUEST));
        authContext.setMemberId(memberId);
        return true;
    }

    private boolean isAuthenticationNotRequired(HttpServletRequest request) {
        HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());
        String requestURI = request.getRequestURI();
        PathMatcher pathMatcher = pathMatcherProvider.getIfAvailable();
        return authNotRequiredConditions.stream()
                .anyMatch(it -> it.match(pathMatcher, requestURI, httpMethod));
    }

    private HttpSession getSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new AuthException(UNAUTHORIZED_REQUEST);
        }
        return session;
    }

    public record UriAndMethodsCondition(
            String uriPattern,
            Set<HttpMethod> httpMethods
    ) {

        public boolean match(PathMatcher pathMatcher, String requestURI, HttpMethod httpMethod) {
            return pathMatcher.match(uriPattern, requestURI) && httpMethods.contains(httpMethod);
        }
    }
}
