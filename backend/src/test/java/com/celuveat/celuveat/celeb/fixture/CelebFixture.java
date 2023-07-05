package com.celuveat.celuveat.celeb.fixture;

import com.celuveat.celuveat.celeb.Celeb;

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
}
