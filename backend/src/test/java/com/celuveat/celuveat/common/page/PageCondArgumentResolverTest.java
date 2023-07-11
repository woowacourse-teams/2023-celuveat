package com.celuveat.celuveat.common.page;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;

@DisplayName("PageCondArgumentResolver 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class PageCondArgumentResolverTest {

    private final PageCondArgumentResolver resolver = new PageCondArgumentResolver();

    @Test
    void 페이지는_1페이지가_처음이며_이보다_작은_페이지는_1페이지로_설정된다() {
        // given
        MethodParameter methodParameter = mock(MethodParameter.class);
        NativeWebRequest webRequest = mock(NativeWebRequest.class);
        Page pageAnno = mock(Page.class);
        given(webRequest.getParameter("page")).willReturn("0");
        given(webRequest.getParameter("size")).willReturn("20");
        given(methodParameter.getParameterAnnotation(Page.class))
                .willReturn(pageAnno);
        given(pageAnno.page()).willReturn(1);
        given(pageAnno.size()).willReturn(20);

        // when
        PageCond result = resolver.resolveArgument(methodParameter, null, webRequest, null);

        // then
        assertThat(result.page()).isEqualTo(1);
        assertThat(result.size()).isEqualTo(20);
    }

    @Test
    void 사이즈는_50보다_크면_어노테이션에_설정된_값으로_설정된다_최대이다() {
        // when
        MethodParameter methodParameter = mock(MethodParameter.class);
        NativeWebRequest webRequest = mock(NativeWebRequest.class);
        Page pageAnno = mock(Page.class);
        given(webRequest.getParameter("page")).willReturn("0");
        given(webRequest.getParameter("size")).willReturn("100");
        given(methodParameter.getParameterAnnotation(Page.class))
                .willReturn(pageAnno);
        given(pageAnno.page()).willReturn(1);
        given(pageAnno.size()).willReturn(52);

        // when
        PageCond result = resolver.resolveArgument(methodParameter, null, webRequest, null);

        // then
        assertThat(result.page()).isEqualTo(1);
        assertThat(result.size()).isEqualTo(52);
    }

    @Test
    void 사이즈가_1보다_작으면_어노테이션에_설정된_값으로_설정된다() {
        // when
        MethodParameter methodParameter = mock(MethodParameter.class);
        NativeWebRequest webRequest = mock(NativeWebRequest.class);
        Page pageAnno = mock(Page.class);
        given(webRequest.getParameter("page")).willReturn("0");
        given(webRequest.getParameter("size")).willReturn("0");
        given(methodParameter.getParameterAnnotation(Page.class))
                .willReturn(pageAnno);
        given(pageAnno.page()).willReturn(1);
        given(pageAnno.size()).willReturn(2);

        // when
        PageCond result = resolver.resolveArgument(methodParameter, null, webRequest, null);

        // then
        assertThat(result.page()).isEqualTo(1);
        assertThat(result.size()).isEqualTo(2);
    }
}
