package com.celuveat.celuveat.celeb.infra.youtube;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.celeb.domain.CelebFetcher;
import com.celuveat.celuveat.celeb.infra.youtube.api.Channel;
import com.celuveat.celuveat.celeb.infra.youtube.api.MockYouTubeChannelApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("YouTubeCelebFetcher 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class YouTubeCelebFetcherTest {

    private final CelebFetcher celebFetcher = new YouTubeCelebFetcher(new MockYouTubeChannelApi());

    @Test
    void 채널_아이디로_셀럽_정보를_가져온다() {
        // given
        String channelId = Channel.TZUYANG.channelId();

        // when
        Celeb result = celebFetcher.fetchCelebByChannelId(channelId);

        // then
        assertAll(
                () -> assertThat(result.id()).isNull(),
                () -> assertThat(result.name()).isEqualTo("tzuyang쯔양"),
                () -> assertThat(result.youtubeChannelId()).isEqualTo(channelId),
                () -> assertThat(result.youtubeChannelName()).isEqualTo("@tzuyang6145"),
                () -> assertThat(result.subscriberCount()).isEqualTo(8_420_000),
                () -> assertThat(result.youtubeChannelUrl()).isEqualTo("https://www.youtube.com/@tzuyang6145"),
                () -> assertThat(result.profileImageUrl()).isEqualTo("https://yt3.ggpht.com/_s3C7XpwVKVr_"
                        + "5gDrmYJh9AOkU3wFlY9FWyZBVGVP_v7B09P5O4bbEZaWGpiuyT78Dk-aElE=s800-c-k-c0x00ffffff-no-rj")
        );
    }
}
