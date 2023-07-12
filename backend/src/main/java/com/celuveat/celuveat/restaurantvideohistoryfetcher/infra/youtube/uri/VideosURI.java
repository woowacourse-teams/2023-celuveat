package com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube.uri;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.util.UriComponentsBuilder;

public class VideosURI {

    private static final String API_URI = "https://www.googleapis.com/youtube/v3/videos";

    private VideosURI() {
    }

    public static Builder builder(String apiKey, String videoId) {
        return new Builder(apiKey, videoId);
    }

    public static class Builder {

        private final Map<String, String> params;

        public Builder(String apiKey, String videoId) {
            params = new HashMap<>();
            params.put("key", apiKey);
            params.put("id", videoId);
            params.put("part", "snippet,statistics");
        }

        public URI build() {
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(API_URI);
            params.forEach(builder::queryParam);
            return builder.build().toUri();
        }
    }
}
