package com.celuveat.auth.presentation;

import static com.celuveat.auth.presentation.AuthConstant.JSESSION_ID;

import com.celuveat.common.auth.LooseAuth;
import com.celuveat.common.optional.CustomOptional;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
                && parameter.getParameterType().equals(CustomOptional.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpSession session = request.getSession(false);
        if (session == null) {
            return CustomOptional.empty();
        }
        return CustomOptional.of(session.getAttribute(JSESSION_ID));
    }
}
