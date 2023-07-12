package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.uri;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("VideosURI 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class VideosURITest {

    @Test
    void 비디오는_파트를_기본으로_갖는다() {
        // when
        String result = VideosURI.builder("secret_key", "abc")
                .build()
                .toString();

        // then
        assertThat(result).contains(
                "https://www.googleapis.com/youtube/v3/videos",
                "key=secret_key",
                "id=abc",
                "part=snippet,statistics"
        );
    }
}
