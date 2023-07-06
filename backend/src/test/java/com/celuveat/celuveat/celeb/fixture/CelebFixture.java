package com.celuveat.celuveat.celeb.fixture;

import com.celuveat.celuveat.celeb.domain.Celeb;

public class CelebFixture {

    public static Celeb 히밥() {
        return Celeb.builder()
                .name("히밥")
                .youtubeId("@heebab")
                .subscriberCount(1_000_000)
                .link("https://naver.com")
                .backgroundImageUrl("https://google.com")
                .profileImageUrl("https://image.com")
                .build();
    }

    public static Celeb 성시경() {
        return Celeb.builder()
                .name("성시경")
                .youtubeId("@sikyung")
                .subscriberCount(1_000_001)
                .link("https://sikyung.com")
                .backgroundImageUrl("https://sikyung.background.com")
                .profileImageUrl("https://sikyung.image.com")
                .build();
    }
}
