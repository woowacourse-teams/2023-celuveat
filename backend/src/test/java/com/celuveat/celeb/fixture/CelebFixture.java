package com.celuveat.celeb.fixture;

import com.celuveat.celeb.domain.Celeb;

public class CelebFixture {

    public static Celeb 셀럽(String name) {
        return Celeb.builder()
                .name(name)
                .profileImageUrl(name)
                .youtubeChannelName(name)
                .build();
    }
}
