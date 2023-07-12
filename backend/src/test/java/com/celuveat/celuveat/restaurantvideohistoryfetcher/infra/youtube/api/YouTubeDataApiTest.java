package com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube.dto.search.SearchListResponse;
import com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube.dto.video.Item;
import com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube.dto.video.Snippet;
import com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube.dto.video.Statistics;
import com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube.dto.video.Thumbnail;
import com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube.dto.video.VideoListResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

@DisplayName("YouTubeDataApi 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class YouTubeDataApiTest {

    @Test
    @Disabled
    void 실제_서버에서_데이터를_가져올수있다() {
        // given
        YouTubeDataApi youTubeDataApi = new YouTubeDataApi("secret_key", new RestTemplate());

        // when
        SearchListResponse result = youTubeDataApi.searchList("UCfpaSruWW3S4dibonKXENjA");

        // then
        assertThat(result.items()).hasSize(50);
    }

    @Test
    @Disabled
    void 실제_서버에서_페이지_토큰으로_다음_응답을_가져올수있다() {
        // given
        YouTubeDataApi youTubeDataApi = new YouTubeDataApi("secret_key", new RestTemplate());
        SearchListResponse response = youTubeDataApi.searchList("UCfpaSruWW3S4dibonKXENjA");
        String nextPageToken = response.nextPageToken();

        // when
        SearchListResponse result = youTubeDataApi.searchList("UCfpaSruWW3S4dibonKXENjA", nextPageToken);

        // then
        assertThat(result.items()).hasSize(50);
    }

    @Test
    @Disabled
    void 실제_서버에서_비디오_정보를_조회할수있다() {
        // given
        YouTubeDataApi youTubeDataApi = new YouTubeDataApi("secret_key", new RestTemplate());

        // when
        VideoListResponse response = youTubeDataApi.searchVideoById("8RdkFuFK1DY");
        Item item = response.items().get(0);
        Snippet snippet = item.snippet();
        Thumbnail thumbnail = snippet.thumbnails().standardThumbnail();
        Statistics statistics = item.statistics();

        // then
        assertAll(
                () -> assertThat(response.items()).hasSize(1),
                () -> assertThat(snippet.title()).isNotNull(),
                () -> assertThat(item.id()).isNotNull(),
                () -> assertThat(thumbnail.url()).isNotNull(),
                () -> assertThat(statistics.viewCount()).isNotNull(),
                () -> assertThat(snippet.publishedAt()).isNotNull()
        );
    }
}
