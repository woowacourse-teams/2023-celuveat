package com.celuveat.celuveat.common.page;

import java.util.Objects;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class PageCondArgumentResolver implements HandlerMethodArgumentResolver {

    private static final int MIN_PAGE = 1;
    private static final int MAX_SIZE = 50;
    private static final int MIN_SIZE = 1;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return PageCond.class.isAssignableFrom(parameter.getParameterType())
                && parameter.hasParameterAnnotation(Page.class);
    }

    @Override
    public PageCond resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        int page = parseInt(webRequest, "page");
        int size = parseInt(webRequest, "size");
        Page pageAnno = parameter.getParameterAnnotation(Page.class);
        return new PageCond(getPage(page, pageAnno), getSize(size, pageAnno));
    }

    private int getPage(int page, Page pageAnno) {
        if (page < MIN_PAGE) {
            return pageAnno.page();
        }
        return page;
    }

    private int getSize(int size, Page pageAnno) {
        if (size < MIN_SIZE || size > MAX_SIZE) {
            return pageAnno.size();
        }
        return size;
    }

    private static int parseInt(NativeWebRequest webRequest, String paramName) {
        try {
            String param = Objects.requireNonNull(webRequest.getParameter(paramName));
            return Integer.parseInt(param);
        } catch (Exception e) {
            return 0;
        }
    }
}
