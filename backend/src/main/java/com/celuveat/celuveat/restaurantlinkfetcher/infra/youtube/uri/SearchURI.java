package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.uri;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.util.UriComponentsBuilder;

public class SearchURI {

    private static final String API_URI = "https://www.googleapis.com/youtube/v3/search";

    private SearchURI() {
    }

    public static Builder builder(String apiKey, String channelId) {
        return new Builder(apiKey, channelId);
    }

    public static class Builder {

        private final Map<String, String> params;

        public Builder(String apiKey, String channelId) {
            params = new HashMap<>();
            params.put("key", apiKey);
            params.put("channelId", channelId);
            params.put("part", "snippet");
            params.put("maxResults", "50");
            params.put("order", "date");
        }

        public Builder pageToken(String pageToken) {
            params.put("pageToken", pageToken);
            return this;
        }

        public URI build() {
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(API_URI);
            params.forEach(builder::queryParam);
            return builder.build().toUri();
        }
    }
}
