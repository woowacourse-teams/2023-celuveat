package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.search.SearchListResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("MockYouTubeDataApiImpl 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MockYouTubeDataApiImplTest {

    @Test
    void 미리_설정된_데이터를_반환한다() {
        // given
        YouTubeDataApi youTubeDataApi = new MockYouTubeDataApiImpl();

        // when
        SearchListResponse response = youTubeDataApi.searchList("a");

        // then
        assertThat(response.items()).hasSize(50);
    }

    @Test
    void 페이지_토큰으로_다음_응답을_가져온다() {
        // given
        YouTubeDataApi youTubeDataApi = new MockYouTubeDataApiImpl();
        SearchListResponse response = youTubeDataApi.searchList("a");
        String nextPageToken = response.nextPageToken();

        // when
        SearchListResponse result = youTubeDataApi.searchList("a", nextPageToken);

        // then
        assertThat(result.items()).hasSize(50);
    }
}
