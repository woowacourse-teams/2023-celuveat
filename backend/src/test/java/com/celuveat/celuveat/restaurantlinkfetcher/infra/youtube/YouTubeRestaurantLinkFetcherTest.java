package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube;

import static com.celuveat.celuveat.celeb.fixture.CelebFixture.쯔양;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.히밥;
import static com.celuveat.celuveat.video.fixture.VideoHistoryFixture.쯔양_001;
import static com.celuveat.celuveat.video.fixture.VideoHistoryFixture.쯔양_002;
import static com.celuveat.celuveat.video.fixture.VideoHistoryFixture.쯔양_003;
import static com.celuveat.celuveat.video.fixture.VideoHistoryFixture.쯔양_004;
import static com.celuveat.celuveat.video.fixture.VideoHistoryFixture.쯔양_005;
import static com.celuveat.celuveat.video.fixture.VideoHistoryFixture.쯔양_006;
import static com.celuveat.celuveat.video.fixture.VideoHistoryFixture.쯔양_007;
import static com.celuveat.celuveat.video.fixture.VideoHistoryFixture.쯔양_008;
import static com.celuveat.celuveat.video.fixture.VideoHistoryFixture.쯔양_009;
import static com.celuveat.celuveat.video.fixture.VideoHistoryFixture.쯔양_010;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.restaurantlinkfetcher.domain.RestaurantLinkFetcher;
import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.api.MockYouTubeDataApi;
import com.celuveat.celuveat.video.domain.VideoHistory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("YouTubeRestaurantLinkFetcher 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class YouTubeRestaurantLinkFetcherTest {

    private final RestaurantLinkFetcher restaurantLinkFetcher =
            new YouTubeRestaurantLinkFetcher(new MockYouTubeDataApi());

    private static Stream<Arguments> getCelebs() {
        return Stream.of(
                Arguments.of(쯔양(), 429),
                Arguments.of(히밥(), 502)
        );
    }

    @ParameterizedTest
    @MethodSource("getCelebs")
    void 한_채널의_모든_영상_이력을_반환한다(Celeb celeb, int videoCount) {
        // when
        List<VideoHistory> videoHistories = restaurantLinkFetcher.fetchAllByCeleb(celeb);

        // then
        assertThat(videoHistories).hasSize(videoCount);
    }

    @Test
    void 비디오_영상_이력이_실제_데이터의_정보를_가진다() {
        // given
        Celeb 쯔양 = 쯔양(1L);
        Long 쯔양_ID = 쯔양.id();
        List<VideoHistory> expected = List.of(
                쯔양_001(쯔양_ID), 쯔양_002(쯔양_ID), 쯔양_003(쯔양_ID), 쯔양_004(쯔양_ID), 쯔양_005(쯔양_ID),
                쯔양_006(쯔양_ID), 쯔양_007(쯔양_ID), 쯔양_008(쯔양_ID), 쯔양_009(쯔양_ID), 쯔양_010(쯔양_ID)
        );

        // when
        List<VideoHistory> videoHistories = restaurantLinkFetcher.fetchAllByCeleb(쯔양);
        List<VideoHistory> result = videoHistories.subList(0, 10);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"2, 21, 60", "3, 19, 50", "4, 10, 40", "5, 7, 30"})
    void 지정한_시간_이후의_모든_영상_이력을_반환한다(int month, int day, int expected) {
        // given
        Celeb 쯔양 = 쯔양();
        LocalDateTime startDateTime = LocalDateTime.of(2023, month, day, 21, 0, 0);

        // when
        List<VideoHistory> result = restaurantLinkFetcher.fetchNewByCeleb(쯔양, startDateTime);

        // then
        assertThat(result).hasSize(expected);
    }

    @ParameterizedTest
    @MethodSource("getCelebs")
    void 지정한_시간이_아주_작으면_모든_영상_이력을_반환한다(Celeb celeb, int videoCount) {
        // when
        List<VideoHistory> result = restaurantLinkFetcher.fetchNewByCeleb(celeb, LocalDateTime.MIN);

        // then
        assertThat(result).hasSize(videoCount);
    }

    @ParameterizedTest
    @MethodSource("getCelebs")
    void 지정한_시간이_아주_크면_아무_영상_이력도_반환하지_않는다(Celeb celeb) {
        // when
        List<VideoHistory> result = restaurantLinkFetcher.fetchNewByCeleb(celeb, LocalDateTime.MAX);

        // then
        assertThat(result).isEmpty();
    }
}
