package com.celuveat.celuveat.video.infra.youtube.api;

import com.celuveat.celuveat.video.infra.youtube.dto.search.SearchListResponse;
import com.celuveat.celuveat.video.infra.youtube.dto.video.VideoListResponse;
import com.celuveat.celuveat.video.infra.youtube.uri.SearchURI;
import com.celuveat.celuveat.video.infra.youtube.uri.VideosURI;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class YouTubeVideoApi {

    private final String apiKey;
    private final RestTemplate restTemplate;

    public SearchListResponse searchVideosByChannelId(String channelId) {
        URI uri = SearchURI.builder(apiKey, channelId)
                .build();
        return restTemplate.getForObject(uri, SearchListResponse.class);
    }

    public SearchListResponse searchVideosByChannelIdAndPageToken(String channelId, String pageToken) {
        URI uri = SearchURI.builder(apiKey, channelId)
                .pageToken(pageToken)
                .build();
        return restTemplate.getForObject(uri, SearchListResponse.class);
    }

    public VideoListResponse searchVideoById(String videoId) {
        URI uri = VideosURI.builder(apiKey, videoId)
                .build();
        return restTemplate.getForObject(uri, VideoListResponse.class);
    }
}
