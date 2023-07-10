package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.api;

import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.search.SearchListResponse;
import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.uri.SearchURI;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class YouTubeDataApi {

    private final String apiKey;
    private final RestTemplate restTemplate;

    public SearchListResponse searchList(String channelId) {
        URI uri = SearchURI.builder(apiKey, channelId)
                .build();
        return restTemplate.getForObject(uri, SearchListResponse.class);
    }

    public SearchListResponse searchList(String channelId, String pageToken) {
        URI uri = SearchURI.builder(apiKey, channelId)
                .pageToken(pageToken)
                .build();
        return restTemplate.getForObject(uri, SearchListResponse.class);
    }
}
