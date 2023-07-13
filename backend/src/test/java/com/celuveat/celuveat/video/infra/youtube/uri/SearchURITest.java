package com.celuveat.celuveat.video.infra.youtube.uri;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("SearchURI 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class SearchURITest {

    @Test
    void 검색은_파트와_정렬을_기본으로_갖는다() {
        // when
        String result = SearchURI.builder("secret_key", "abc")
                .build()
                .toString();

        // then
        assertThat(result).contains(
                "https://www.googleapis.com/youtube/v3/search",
                "key=secret_key",
                "channelId=abc",
                "part=snippet",
                "maxResults=50",
                "order=date"
        );
    }

    @Test
    void 검색은_페이지_토큰을_받을수있다() {
        // when
        String result = SearchURI.builder("secret_key", "abc")
                .pageToken("token")
                .build()
                .toString();

        // then
        assertThat(result).contains(
                "https://www.googleapis.com/youtube/v3/search",
                "key=secret_key",
                "channelId=abc",
                "part=snippet",
                "maxResults=50",
                "order=date",
                "pageToken=token"
        );
    }
}
