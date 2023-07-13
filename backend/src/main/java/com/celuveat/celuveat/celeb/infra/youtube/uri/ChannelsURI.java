package com.celuveat.celuveat.celeb.infra.youtube.uri;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.util.UriComponentsBuilder;

public class ChannelsURI {

    private static final String API_URI = "https://www.googleapis.com/youtube/v3/channels";

    private ChannelsURI() {
    }

    public static Builder builder(String apiKey, String channelId) {
        return new Builder(apiKey, channelId);
    }

    public static class Builder {

        private final Map<String, String> params;

        public Builder(String apiKey, String channelId) {
            params = new HashMap<>();
            params.put("key", apiKey);
            params.put("id", channelId);
            params.put("part", "snippet,statistics");
        }

        public URI build() {
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(API_URI);
            params.forEach(builder::queryParam);
            return builder.build().toUri();
        }
    }
}
