package com.celuveat.celuveat.celeb.infra.youtube.api;

import com.celuveat.celuveat.celeb.infra.youtube.dto.channels.ChannelListResponse;
import com.celuveat.celuveat.celeb.infra.youtube.uri.ChannelsURI;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class YouTubeChannelApi {

    private final String apiKey;
    private final RestTemplate restTemplate;

    public ChannelListResponse searchChannelById(String channelId) {
        URI uri = ChannelsURI.builder(apiKey, channelId)
                .build();
        return restTemplate.getForObject(uri, ChannelListResponse.class);
    }
}
