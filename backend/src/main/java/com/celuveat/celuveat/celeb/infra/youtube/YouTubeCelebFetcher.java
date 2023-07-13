package com.celuveat.celuveat.celeb.infra.youtube;

import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.celeb.domain.CelebFetcher;
import com.celuveat.celuveat.celeb.infra.youtube.api.YouTubeChannelApi;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class YouTubeCelebFetcher implements CelebFetcher {

    private final YouTubeChannelApi youTubeChannelApi;

    @Override
    public Celeb fetchCelebByChannelId(String channelId) {
        return youTubeChannelApi.searchChannelById(channelId).toCeleb();
    }
}
