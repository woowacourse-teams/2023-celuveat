package com.celuveat.celuveat.celeb.infra.youtube.uri;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("ChannelsURI 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class ChannelsURITest {

    @Test
    void 채널은_파트를_기본으로_갖는다() {
        // when
        String result = ChannelsURI.builder("secret_key", "abc")
                .build()
                .toString();

        // then
        assertThat(result).contains(
                "https://www.googleapis.com/youtube/v3/channels",
                "key=secret_key",
                "id=abc",
                "part=snippet,statistics"
        );
    }
}
