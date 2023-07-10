package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.api;

import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.search.SearchListResponse;
import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.uri.SearchURI;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class YouTubeDataApiImpl implements YouTubeDataApi {

    private final String apiKey;

    @Override
    public SearchListResponse searchList(String channelId) {
        URI uri = SearchURI.builder(apiKey, channelId)
                .build();
        return new RestTemplate().getForObject(uri, SearchListResponse.class);
    }

    @Override
    public SearchListResponse searchList(String channelId, String pageToken) {
        URI uri = SearchURI.builder(apiKey, channelId)
                .pageToken(pageToken)
                .build();
        return new RestTemplate().getForObject(uri, SearchListResponse.class);
    }
}
