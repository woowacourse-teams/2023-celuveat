package com.celuveat.celuveat.celeb.fixture;

import com.celuveat.celuveat.celeb.application.dto.FindAllCelebResponse;
import com.celuveat.celuveat.celeb.application.dto.FindCelebByIdResponse;
import com.celuveat.celuveat.celeb.domain.Celeb;

@SuppressWarnings("NonAsciiCharacters")
public class CelebFixture {

    public static Celeb 쯔양() {
        return Celeb.builder()
                .name("tzuyang쯔양")
                .youtubeChannelName("@tzuyang6145")
                .youtubeChannelId("UCfpaSruWW3S4dibonKXENjA")
                .subscriberCount(8_410_000)
                .youtubeChannelUrl("https://www.youtube.com/@tzuyang6145")
                .backgroundImageUrl("xxxxxxxxxx")
                .profileImageUrl("https://yt3.ggpht.com/_s3C7XpwVKVr_5gDrmYJh9AOkU3wFlY9FWyZBVGVP"
                        + "_v7B09P5O4bbEZaWGpiuyT78Dk-aElE=s800-c-k-c0x00ffffff-no-rj")
                .build();
    }

    public static Celeb 쯔양(Long id) {
        return Celeb.builder()
                .id(id)
                .name("tzuyang쯔양")
                .youtubeChannelName("@tzuyang6145")
                .youtubeChannelId("UCfpaSruWW3S4dibonKXENjA")
                .subscriberCount(8_410_000)
                .youtubeChannelUrl("https://www.youtube.com/@tzuyang6145")
                .backgroundImageUrl("xxxxxxxxxx")
                .profileImageUrl("https://yt3.ggpht.com/_s3C7XpwVKVr_5gDrmYJh9AOkU3wFlY9FWyZBVGVP"
                        + "_v7B09P5O4bbEZaWGpiuyT78Dk-aElE=s800-c-k-c0x00ffffff-no-rj")
                .build();
    }

    public static Celeb 히밥() {
        return Celeb.builder()
                .name("히밥heebab")
                .youtubeChannelName("@heebab")
                .youtubeChannelId("UCA6KBBX8cLwYZNepxlE_7SA")
                .subscriberCount(1_500_000)
                .youtubeChannelUrl("https://www.youtube.com/@heebab")
                .backgroundImageUrl("xxxxxxxxxx")
                .profileImageUrl("https://yt3.ggpht.com/sL5ugPfl9vvwRwhf6l5APY_"
                        + "_BZBw8qWiwgHs-uVsMPFoD5-a4opTJIcRSyrY8aY5LEESOMWJ=s800-c-k-c0x00ffffff-no-rj")
                .build();
    }

    public static Celeb 히밥(Long id) {
        return Celeb.builder()
                .id(id)
                .name("히밥heebab")
                .youtubeChannelName("@heebab")
                .youtubeChannelId("UCA6KBBX8cLwYZNepxlE_7SA")
                .subscriberCount(1_500_000)
                .youtubeChannelUrl("https://www.youtube.com/@heebab")
                .backgroundImageUrl("xxxxxxxxxx")
                .profileImageUrl("https://yt3.ggpht.com/sL5ugPfl9vvwRwhf6l5APY_"
                        + "_BZBw8qWiwgHs-uVsMPFoD5-a4opTJIcRSyrY8aY5LEESOMWJ=s800-c-k-c0x00ffffff-no-rj")
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
