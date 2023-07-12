package com.celuveat.celuveat.celeb.fixture;

import com.celuveat.celuveat.celeb.application.dto.FindAllCelebResponse;
import com.celuveat.celuveat.celeb.application.dto.FindCelebByIdResponse;
import com.celuveat.celuveat.celeb.application.dto.RegisterCelebCommand;
import com.celuveat.celuveat.celeb.domain.Celeb;

@SuppressWarnings("NonAsciiCharacters")
public class CelebFixture {

    public static Celeb 쯔양() {
        return Celeb.builder()
                .name("쯔양")
                .youtubeChannelName("@tzuyang")
                .youtubeChannelId("UCfpaSruWW3S4dibonKXENjA")
                .subscriberCount(1_000_000)
                .youtubeChannelUrl("https://naver.com")
                .backgroundImageUrl("https://google.com")
                .profileImageUrl("https://image.com")
                .build();
    }

    public static Celeb 쯔양(Long id) {
        return Celeb.builder()
                .id(id)
                .name("쯔양")
                .youtubeChannelName("@tzuyang")
                .youtubeChannelId("UCfpaSruWW3S4dibonKXENjA")
                .subscriberCount(1_000_000)
                .youtubeChannelUrl("https://naver.com")
                .backgroundImageUrl("https://google.com")
                .profileImageUrl("https://image.com")
                .build();
    }

    public static Celeb 히밥() {
        return Celeb.builder()
                .name("히밥")
                .youtubeChannelName("@heebab")
                .youtubeChannelId("UCA6KBBX8cLwYZNepxlE_7SA")
                .subscriberCount(1_000_000)
                .youtubeChannelUrl("https://naver.com")
                .backgroundImageUrl("https://google.com")
                .profileImageUrl("https://image.com")
                .build();
    }

    public static Celeb 히밥(Long id) {
        return Celeb.builder()
                .id(id)
                .name("히밥")
                .youtubeChannelName("@heebab")
                .youtubeChannelId("UCA6KBBX8cLwYZNepxlE_7SA")
                .subscriberCount(1_000_000)
                .youtubeChannelUrl("https://naver.com")
                .backgroundImageUrl("https://google.com")
                .profileImageUrl("https://image.com")
                .build();
    }

    public static Celeb 성시경() {
        return Celeb.builder()
                .name("성시경")
                .youtubeChannelName("@sikyung")
                .youtubeChannelId("YOUTUBE_CHANNEL_ID")
                .subscriberCount(1_000_001)
                .youtubeChannelUrl("https://sikyung.com")
                .backgroundImageUrl("https://sikyung.background.com")
                .profileImageUrl("https://sikyung.image.com")
                .build();
    }

    public static Celeb 성시경(Long id) {
        return Celeb.builder()
                .id(id)
                .name("성시경")
                .youtubeChannelName("@sikyung")
                .youtubeChannelId("YOUTUBE_CHANNEL_ID")
                .subscriberCount(1_000_001)
                .youtubeChannelUrl("https://sikyung.com")
                .backgroundImageUrl("https://sikyung.background.com")
                .profileImageUrl("https://sikyung.image.com")
                .build();
    }

    public static RegisterCelebCommand 히밥_저장_요청() {
        return RegisterCelebCommand.builder()
                .name("히밥")
                .youtubeChannelName("@heebab")
                .youtubeChannelId("YOUTUBE_CHANNEL_ID")
                .subscriberCount(1_000_000)
                .youtubeChannelUrl("https://naver.com")
                .backgroundImageUrl("https://google.com")
                .profileImageUrl("https://image.com")
                .build();
    }

    public static FindCelebByIdResponse toFindCelebByIdResponse(Celeb celeb, int restaurantCount) {
        return new FindCelebByIdResponse(
                celeb.id(),
                celeb.name(),
                celeb.youtubeChannelName(),
                celeb.subscriberCount(),
                celeb.youtubeChannelUrl(),
                celeb.backgroundImageUrl(),
                celeb.profileImageUrl(),
                restaurantCount
        );
    }

    public static FindAllCelebResponse toFindAllCelebResponse(Celeb celeb) {
        return new FindAllCelebResponse(
                celeb.id(),
                celeb.name(),
                celeb.youtubeChannelName(),
                celeb.subscriberCount(),
                celeb.profileImageUrl()
        );
    }
}
