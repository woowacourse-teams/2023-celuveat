package com.celuveat.celuveat.celeb.infra.youtube.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.celeb.infra.youtube.dto.channels.ChannelListResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

@Disabled
@DisplayName("YouTubeChannelApi 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class YouTubeChannelApiTest {

    @Test
    void 실제_서버에서_채널_정보를_조회할수있다() {
        // given
        YouTubeChannelApi youTubeChannelApi = new YouTubeChannelApi("secret_key", new RestTemplate());

        // when
        ChannelListResponse result = youTubeChannelApi.searchChannelById("UCfpaSruWW3S4dibonKXENjA");

        // then
        assertThat(result).isNotNull();
    }
}
