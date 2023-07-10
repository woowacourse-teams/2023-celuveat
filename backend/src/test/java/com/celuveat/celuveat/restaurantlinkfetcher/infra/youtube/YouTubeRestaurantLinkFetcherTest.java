package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube;

import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.restaurantlinkfetcher.domain.RestaurantLinkFetcher;
import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.api.MockYouTubeDataApiImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("YouTubeRestaurantLinkFetcher 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class YouTubeRestaurantLinkFetcherTest {

    @Test
    void 한_채널의_모든_영상들의_링크를_반환한다() {
        // given
        RestaurantLinkFetcher restaurantLinkFetcher =
                new YouTubeRestaurantLinkFetcher(new MockYouTubeDataApiImpl());

        // when
        List<String> videoIds = restaurantLinkFetcher.fetchAllByChannelId("a");

        // then
        assertThat(videoIds).hasSize(429);
    }

    @ParameterizedTest
    @CsvSource({"2, 21, 60", "3, 19, 50", "4, 10, 40", "5, 7, 30"})
    void 지정한_시간_이후의_모든_영상들의_링크를_반환한다(int month, int day, int expected) {
        // given
        RestaurantLinkFetcher restaurantLinkFetcher =
                new YouTubeRestaurantLinkFetcher(new MockYouTubeDataApiImpl());
        LocalDateTime startDateTime = LocalDateTime.of(2023, month, day, 21, 0, 0);
        
        // when
        List<String> result = restaurantLinkFetcher.fetchNewByChannelId("a", startDateTime);

        // then
        assertThat(result).hasSize(expected);
    }

    @Test
    void 지정한_시간이_아주_작으면_모든_영상들의_링크를_반환한다() {
        // given
        RestaurantLinkFetcher restaurantLinkFetcher =
                new YouTubeRestaurantLinkFetcher(new MockYouTubeDataApiImpl());

        // when
        List<String> result = restaurantLinkFetcher.fetchNewByChannelId("a", LocalDateTime.MIN);

        // then
        assertThat(result).hasSize(429);
    }

    @Test
    void 지정한_시간이_아주_크면_아무_영상도_반환하지_않는다() {
        // given
        RestaurantLinkFetcher restaurantLinkFetcher =
                new YouTubeRestaurantLinkFetcher(new MockYouTubeDataApiImpl());

        // when
        List<String> result = restaurantLinkFetcher.fetchNewByChannelId("a", LocalDateTime.MAX);

        // then
        assertThat(result).isEmpty();
    }
}
