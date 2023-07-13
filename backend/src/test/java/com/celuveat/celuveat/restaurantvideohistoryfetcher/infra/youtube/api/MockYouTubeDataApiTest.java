package com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube.api;

import static com.celuveat.celuveat.restaurantvideohistoryfetcher.exception.VideoHistoryFetcherExceptionType.NOT_FOUND_RESTAURANT_LINK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.celuveat.common.exception.BaseExceptionType;
import com.celuveat.celuveat.restaurantvideohistoryfetcher.exception.VideoHistoryFetcherException;
import com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube.dto.search.SearchListResponse;
import com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube.dto.search.Snippet;
import com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube.dto.video.Item;
import com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube.dto.video.Statistics;
import com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube.dto.video.VideoListResponse;
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
        BaseExceptionType exceptionType = assertThrows(VideoHistoryFetcherException.class, () ->
                youTubeDataApi.searchVideosByChannelId("a")
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
        SearchListResponse response = youTubeDataApi.searchVideosByChannelId(channelId);
        Snippet snippet = response.items().get(0).snippet();

        // then
        assertAll(
                () -> assertThat(response.items()).hasSize(50),
                () -> assertThat(snippet.channelId()).isEqualTo(channelId),
                () -> assertThat(snippet.channelTitle()).isEqualTo(name)
        );
    }

    @ParameterizedTest
    @MethodSource("getChannels")
    void 채널_아이디와_페이지_토큰으로_조회시_다음_응답을_반환한다(Channel channel, String name) {
        // given
        String channelId = channel.channelId();
        SearchListResponse response = youTubeDataApi.searchVideosByChannelId(channelId);
        String nextPageToken = response.nextPageToken();
        LocalDateTime pageOnePublishedAt = response.items().get(0).publishedAt();

        // when
        SearchListResponse result = youTubeDataApi.searchVideosByChannelIdAndPageToken(channelId, nextPageToken);
        Snippet snippet = result.items().get(0).snippet();
        LocalDateTime pageTwoPublishedAt = result.items().get(0).publishedAt();

        // then
        assertAll(
                () -> assertThat(result.items()).hasSize(50),
                () -> assertThat(snippet.channelId()).isEqualTo(channelId),
                () -> assertThat(snippet.channelTitle()).isEqualTo(name),
                () -> assertThat(pageTwoPublishedAt.isBefore(pageOnePublishedAt)).isTrue()
        );
    }

    @Test
    void 존재하지_않는_비디오_아이디로_조회시_기본값을_반환한다() {
        // when
        VideoListResponse response = youTubeDataApi.searchVideoById("a");
        Item item = response.items().get(0);
        String title = item.snippet().title();
        String thumbnailUrl = item.snippet().thumbnails().standardThumbnail().url();
        Statistics statistics = item.statistics();

        // then
        assertAll(
                () -> assertThat(response.items()).hasSize(1),
                () -> assertThat(title).isEqualTo("이만큼 시켰더니 단체손님인줄 아셨대요🤣 방이동 미친비주얼 간장게장 먹방"),
                () -> assertThat(item.id()).isEqualTo("8RdkFuFK1DY"),
                () -> assertThat(thumbnailUrl).isEqualTo("https://i.ytimg.com/vi/8RdkFuFK1DY/sddefault.jpg"),
                () -> assertThat(statistics.viewCount()).isEqualTo("1505107"),
                () -> assertThat(item.snippet().publishedAt()).isEqualTo("2023-07-08T12:00:06Z")
        );
    }

    @Test
    void 비디오_아이디로_조회시_미리_설정된_데이터를_반환한다() {
        // given
        String videoId = "NrLPC4raEh4";

        // when
        VideoListResponse response = youTubeDataApi.searchVideoById(videoId);
        Item item = response.items().get(0);
        String title = item.snippet().title();
        String thumbnailUrl = item.snippet().thumbnails().standardThumbnail().url();
        Statistics statistics = item.statistics();

        // then
        assertAll(
                () -> assertThat(response.items()).hasSize(1),
                () -> assertThat(title).isEqualTo("200만원으로 시장 털었습니다?😳 순천재래시장 먹방"),
                () -> assertThat(item.id()).isEqualTo(videoId),
                () -> assertThat(thumbnailUrl).isEqualTo("https://i.ytimg.com/vi/NrLPC4raEh4/sddefault.jpg"),
                () -> assertThat(statistics.viewCount()).isEqualTo("1528713"),
                () -> assertThat(item.snippet().publishedAt()).isEqualTo("2023-07-06T12:00:31Z")
        );
    }
}
