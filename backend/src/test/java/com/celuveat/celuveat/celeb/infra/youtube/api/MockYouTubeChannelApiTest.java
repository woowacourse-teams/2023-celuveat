package com.celuveat.celuveat.celeb.infra.youtube.api;

import static com.celuveat.celuveat.celeb.exception.CelebFetcherExceptionType.NOT_FOUND_CELEB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.celuveat.celuveat.celeb.exception.CelebFetcherException;
import com.celuveat.celuveat.celeb.infra.youtube.dto.channels.ChannelListResponse;
import com.celuveat.celuveat.celeb.infra.youtube.dto.channels.Item;
import com.celuveat.celuveat.celeb.infra.youtube.dto.channels.Snippet;
import com.celuveat.celuveat.celeb.infra.youtube.dto.channels.Statistics;
import com.celuveat.celuveat.celeb.infra.youtube.dto.channels.Thumbnail;
import com.celuveat.celuveat.common.exception.BaseExceptionType;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("MockYouTubeChannelApi 은(는)")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MockYouTubeChannelApiTest {

    private final YouTubeChannelApi youTubeChannelApi = new MockYouTubeChannelApi();

    private static Stream<Arguments> getChannels() {
        return Stream.of(
                Arguments.of(
                        Channel.TZUYANG,
                        "tzuyang쯔양",
                        "@tzuyang6145",
                        "8420000",
                        "https://yt3.ggpht.com/_s3C7XpwVKVr_5gDrmYJh9AOkU3wFlY9FWyZBVGVP"
                                + "_v7B09P5O4bbEZaWGpiuyT78Dk-aElE=s800-c-k-c0x00ffffff-no-rj"
                ),
                Arguments.of(
                        Channel.HEEBAB,
                        "히밥heebab",
                        "@heebab",
                        "1500000",
                        "https://yt3.ggpht.com/sL5ugPfl9vvwRwhf6l5APY__"
                                + "BZBw8qWiwgHs-uVsMPFoD5-a4opTJIcRSyrY8aY5LEESOMWJ=s800-c-k-c0x00ffffff-no-rj"
                )
        );
    }

    @Test
    void 존재하지_않는_채널_아이디로_조회시_예외가_발생한다() {
        // when
        BaseExceptionType exceptionType = assertThrows(CelebFetcherException.class, () ->
                youTubeChannelApi.searchChannelById("a")
        ).exceptionType();

        // then
        assertThat(exceptionType).isEqualTo(NOT_FOUND_CELEB);
    }

    @ParameterizedTest
    @MethodSource("getChannels")
    void 채널_아이디로_조회시_미리_설정된_데이터를_반환한다(
            Channel channel,
            String name,
            String youtubeChannelName,
            String subscriberCount,
            String profileImageUrl
    ) {
        // given
        String channelId = channel.channelId();

        // when
        ChannelListResponse result = youTubeChannelApi.searchChannelById(channelId);
        Item item = result.items().get(0);
        Snippet snippet = item.snippet();
        Statistics statistics = item.statistics();
        Thumbnail highThumbnail = snippet.thumbnails().highThumbnail();

        // then
        assertAll(
                () -> assertThat(snippet.title()).isEqualTo(name),
                () -> assertThat(snippet.customUrl()).isEqualTo(youtubeChannelName),
                () -> assertThat(item.id()).isEqualTo(channelId),
                () -> assertThat(statistics.subscriberCount()).isEqualTo(subscriberCount),
                () -> assertThat(highThumbnail.url()).isEqualTo(profileImageUrl)
        );
    }
}
