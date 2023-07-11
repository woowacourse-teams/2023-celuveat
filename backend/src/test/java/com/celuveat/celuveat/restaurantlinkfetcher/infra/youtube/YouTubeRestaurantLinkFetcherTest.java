package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube;

import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.restaurantlinkfetcher.domain.RestaurantLinkFetcher;
import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.api.Channel;
import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.api.MockYouTubeDataApi;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("YouTubeRestaurantLinkFetcher 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class YouTubeRestaurantLinkFetcherTest {

    private static Stream<Arguments> getChannels() {
        return Stream.of(
                Arguments.of(Channel.TZUYANG, 429),
                Arguments.of(Channel.HEEBAB, 502)
        );
    }

    @ParameterizedTest
    @MethodSource("getChannels")
    void 한_채널의_모든_영상들의_링크를_반환한다(Channel channel, int videoCount) {
        // given
        RestaurantLinkFetcher restaurantLinkFetcher =
                new YouTubeRestaurantLinkFetcher(new MockYouTubeDataApi());
        String channelId = channel.channelId();

        // when
        List<String> videoIds = restaurantLinkFetcher.fetchAllByChannelId(channelId);

        // then
        assertThat(videoIds).hasSize(videoCount);
    }

    @ParameterizedTest
    @CsvSource({"2, 21, 60", "3, 19, 50", "4, 10, 40", "5, 7, 30"})
    void 지정한_시간_이후의_모든_영상들의_링크를_반환한다(int month, int day, int expected) {
        // given
        RestaurantLinkFetcher restaurantLinkFetcher =
                new YouTubeRestaurantLinkFetcher(new MockYouTubeDataApi());
        String channelId = Channel.TZUYANG.channelId();
        LocalDateTime startDateTime = LocalDateTime.of(2023, month, day, 21, 0, 0);

        // when
        List<String> result = restaurantLinkFetcher.fetchNewByChannelId(channelId, startDateTime);

        // then
        assertThat(result).hasSize(expected);
    }

    @ParameterizedTest
    @MethodSource("getChannels")
    void 지정한_시간이_아주_작으면_모든_영상들의_링크를_반환한다(Channel channel, int videoCount) {
        // given
        RestaurantLinkFetcher restaurantLinkFetcher =
                new YouTubeRestaurantLinkFetcher(new MockYouTubeDataApi());
        String channelId = channel.channelId();

        // when
        List<String> result = restaurantLinkFetcher.fetchNewByChannelId(channelId, LocalDateTime.MIN);

        // then
        assertThat(result).hasSize(videoCount);
    }

    @ParameterizedTest
    @MethodSource("getChannels")
    void 지정한_시간이_아주_크면_아무_영상도_반환하지_않는다(Channel channel) {
        // given
        RestaurantLinkFetcher restaurantLinkFetcher =
                new YouTubeRestaurantLinkFetcher(new MockYouTubeDataApi());
        String channelId = channel.channelId();

        // when
        List<String> result = restaurantLinkFetcher.fetchNewByChannelId(channelId, LocalDateTime.MAX);

        // then
        assertThat(result).isEmpty();
    }
}
