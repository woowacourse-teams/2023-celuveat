package com.celuveat.auth.presentation;

import static com.celuveat.auth.exception.AuthExceptionType.REQUEST_EMPTY;
import static com.celuveat.auth.presentation.AuthConstant.JSESSION_ID;

import com.celuveat.auth.exception.AuthException;
import com.celuveat.common.auth.LooseAuth;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LooseAuthArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LooseAuth.class)
                && parameter.getParameterType().equals(Optional.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        if (httpServletRequest == null) {
            throw new AuthException(REQUEST_EMPTY);
        }
        return Optional.of(httpServletRequest)
                .map(request -> request.getSession(false))
                .map(session -> session.getAttribute(JSESSION_ID));
    }
}
