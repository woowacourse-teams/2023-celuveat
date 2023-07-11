package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.api;

import static com.celuveat.celuveat.restaurantlinkfetcher.exception.RestaurantLinkFetcherExceptionType.NOT_FOUND_RESTAURANT_LINK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.celuveat.common.exception.BaseExceptionType;
import com.celuveat.celuveat.restaurantlinkfetcher.exception.RestaurantLinkFetcherException;
import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.search.SearchListResponse;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("MockYouTubeDataApi 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MockYouTubeDataApiTest {

    private final YouTubeDataApi youTubeDataApi = new MockYouTubeDataApi();

    private static Stream<Arguments> getChannels() {
        return Stream.of(
                Arguments.of(Channel.TZUYANG, "tzuyang쯔양"),
                Arguments.of(Channel.HEEBAB, "히밥heebab")
        );
    }

    @Test
    void 존재하지_않는_채널_아이디로_조회시_예외가_발생한다() {
        // when
        BaseExceptionType exceptionType = assertThrows(RestaurantLinkFetcherException.class, () ->
                youTubeDataApi.searchList("a")
        ).exceptionType();

        // then
        assertThat(exceptionType).isEqualTo(NOT_FOUND_RESTAURANT_LINK);
    }

    @ParameterizedTest
    @MethodSource("getChannels")
    void 채널_아이디로_조회시_미리_설정된_데이터를_반환한다(Channel channel, String name) {
        // given
        String channelId = channel.channelId();

        // when
        SearchListResponse response = youTubeDataApi.searchList(channelId);

        // then
        assertAll(
                () -> assertThat(response.items()).hasSize(50),
                () -> assertThat(response.items().get(0).snippet().channelId()).isEqualTo(channelId),
                () -> assertThat(response.items().get(0).snippet().channelTitle()).isEqualTo(name)
        );
    }

    @ParameterizedTest
    @MethodSource("getChannels")
    void 채널_아이디와_페이지_토큰으로_조회시_다음_응답을_반환한다(Channel channel, String name) {
        // given
        String channelId = channel.channelId();
        SearchListResponse response = youTubeDataApi.searchList(channelId);
        String nextPageToken = response.nextPageToken();
        LocalDateTime pageOnePublishedAt = response.items().get(0).publishedAt();

        // when
        SearchListResponse result = youTubeDataApi.searchList(channelId, nextPageToken);
        LocalDateTime pageTwoPublishedAt = result.items().get(0).publishedAt();

        // then
        assertAll(
                () -> assertThat(result.items()).hasSize(50),
                () -> assertThat(result.items().get(0).snippet().channelId()).isEqualTo(channelId),
                () -> assertThat(result.items().get(0).snippet().channelTitle()).isEqualTo(name),
                () -> assertThat(pageTwoPublishedAt.isBefore(pageOnePublishedAt)).isTrue()
        );
    }
}
