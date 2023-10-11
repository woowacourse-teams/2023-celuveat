package com.celuveat.restaurant.command.domain;

import static com.celuveat.restaurant.exception.RestaurantImageExceptionType.UNSUPPORTED_SOCIAL_MEDIA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.common.exception.BaseException;
import com.celuveat.common.exception.BaseExceptionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("소셜미디어(SocialMedia) 은(는)")
class SocialMediaTest {

    @ParameterizedTest
    @CsvSource({"Youtube", "youtube", "YOUTUBE", "YouTube", " Youtube", "youtube ", " YOUTUBE "})
    void youtube를_변환한다(String value) {
        // when
        SocialMedia socialMedia = SocialMedia.from(value);

        // then
        assertThat(socialMedia).isEqualTo(SocialMedia.YOUTUBE);
    }

    @ParameterizedTest
    @CsvSource({"Instagram", "instagram", "INSTAGRAM", " Instagram", "instagram ", " INSTAGRAM "})
    void instagram을_변환한다(String value) {
        // when
        SocialMedia socialMedia = SocialMedia.from(value);

        // then
        assertThat(socialMedia).isEqualTo(SocialMedia.INSTAGRAM);
    }

    @ParameterizedTest
    @CsvSource({"kakao", "naver"})
    void 존재하지_않는_타입은_예외를_발생시킨다(String value) {
        // when
        BaseExceptionType result = assertThrows(BaseException.class, () ->
                SocialMedia.from(value)
        ).exceptionType();

        // then
        assertThat(result).isEqualTo(UNSUPPORTED_SOCIAL_MEDIA);
    }
}
