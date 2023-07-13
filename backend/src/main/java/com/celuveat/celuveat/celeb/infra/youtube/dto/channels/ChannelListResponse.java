package com.celuveat.celuveat.celeb.infra.youtube.dto.channels;

import com.celuveat.celuveat.celeb.domain.Celeb;
import java.util.List;

public record ChannelListResponse(
        String kind,
        String etag,
        PageInfo pageInfo,
        List<Item> items
) {

    private static final String YOUTUBE_CHANNEL_URL_PREFIX = "https://www.youtube.com/";

    public Celeb toCeleb() {
        Item item = items.get(0);
        String youtubeChannelName = item.snippet().customUrl();
        return Celeb.builder()
                .name(item.snippet().title())
                .youtubeChannelId(item.id())
                .youtubeChannelName(youtubeChannelName)
                .subscriberCount(item.subscriberCount())
                .youtubeChannelUrl(YOUTUBE_CHANNEL_URL_PREFIX + youtubeChannelName)
                .profileImageUrl(item.profileImageUrl())
                .build();
    }
}
