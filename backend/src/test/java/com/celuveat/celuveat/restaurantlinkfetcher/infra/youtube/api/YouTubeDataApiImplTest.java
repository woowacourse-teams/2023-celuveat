package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.search.SearchListResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

@DisplayName("YouTubeDataApiImpl 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class YouTubeDataApiImplTest {

    @Test
    @Disabled
    void 실제_서버에서_데이터를_가져올수있다() {
        // given
        YouTubeDataApiImpl youTubeDataApi = new YouTubeDataApiImpl("secret_key", new RestTemplate());

        // when
        SearchListResponse result = youTubeDataApi.searchList("UCfpaSruWW3S4dibonKXENjA");

        // then
        assertThat(result.items()).hasSize(50);
    }

    @Test
    @Disabled
    void 실제_서버에서_페이지_토큰으로_다음_응답을_가져올수있다() {
        // given
        YouTubeDataApiImpl youTubeDataApi = new YouTubeDataApiImpl("secret_key", new RestTemplate());
        SearchListResponse response = youTubeDataApi.searchList("UCfpaSruWW3S4dibonKXENjA");
        String nextPageToken = response.nextPageToken();

        // when
        SearchListResponse result = youTubeDataApi.searchList("UCfpaSruWW3S4dibonKXENjA", nextPageToken);

        // then
        assertThat(result.items()).hasSize(50);
    }
}
