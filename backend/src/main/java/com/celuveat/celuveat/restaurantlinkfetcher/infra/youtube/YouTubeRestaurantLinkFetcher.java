package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube;

import com.celuveat.celuveat.restaurantlinkfetcher.domain.RestaurantLinkFetcher;
import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.api.YouTubeDataApi;
import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.search.SearchListResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class YouTubeRestaurantLinkFetcher implements RestaurantLinkFetcher {

    private final YouTubeDataApi youTubeDataApi;

    @Override
    public List<String> fetchAllByChannelId(String channelId) {
        SearchListResponse response = youTubeDataApi.searchList(channelId);
        List<String> videoIds = new ArrayList<>(getVideoIds(response));
        return fetchAllByChannelId(channelId, response.nextPageToken(), videoIds);
    }

    private List<String> getVideoIds(SearchListResponse response) {
        return response.items().stream()
                .map(item -> item.id().videoId())
                .toList();
    }

    private List<String> fetchAllByChannelId(String channelId, String nextPageToken, List<String> videoIds) {
        if (nextPageToken == null) {
            return videoIds;
        }
        SearchListResponse response = youTubeDataApi.searchList(channelId, nextPageToken);
        videoIds.addAll(getVideoIds(response));
        return fetchAllByChannelId(channelId, response.nextPageToken(), videoIds);
    }

    @Override
    public List<String> fetchNewByChannelId(String channelId, LocalDateTime startDateTime) {
        SearchListResponse response = youTubeDataApi.searchList(channelId);
        List<String> videoIds = getAfterVideoIds(response, startDateTime);
        if (hasBeforeItem(response, videoIds)) {
            return videoIds;
        }
        List<String> result = new ArrayList<>(videoIds);
        return fetchNewByChannelId(channelId, startDateTime, response.nextPageToken(), result);
    }

    private List<String> getAfterVideoIds(SearchListResponse response, LocalDateTime startDateTime) {
        return response.items().stream()
                .filter(item -> parse(item.snippet().publishedAt()).isAfter(startDateTime))
                .map(item -> item.id().videoId())
                .toList();
    }

    private LocalDateTime parse(String isoDate) {
        return LocalDateTime.from(
                Instant.from(
                        DateTimeFormatter.ISO_DATE_TIME.parse(isoDate)
                ).atZone(ZoneId.of("Asia/Seoul"))
        );
    }

    private boolean hasBeforeItem(SearchListResponse response, List<String> videoIds) {
        return response.items().size() > videoIds.size();
    }

    private List<String> fetchNewByChannelId(
            String channelId,
            LocalDateTime startDateTime,
            String nextPageToken,
            List<String> result
    ) {
        if (nextPageToken == null) {
            return result;
        }
        SearchListResponse response = youTubeDataApi.searchList(channelId, nextPageToken);
        List<String> videoIds = getAfterVideoIds(response, startDateTime);
        result.addAll(videoIds);
        if (hasBeforeItem(response, videoIds)) {
            return result;
        }
        return fetchNewByChannelId(channelId, startDateTime, response.nextPageToken(), result);
    }
}
