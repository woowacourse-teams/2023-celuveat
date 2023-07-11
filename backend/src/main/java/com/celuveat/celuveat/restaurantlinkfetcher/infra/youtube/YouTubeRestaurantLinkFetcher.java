package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube;

import com.celuveat.celuveat.restaurantlinkfetcher.domain.RestaurantLinkFetcher;
import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.api.YouTubeDataApi;
import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.search.SearchListResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class YouTubeRestaurantLinkFetcher implements RestaurantLinkFetcher {

    private final YouTubeDataApi youTubeDataApi;

    @Override
    public List<String> fetchAllByChannelId(String channelId) {
        SearchListResponse response = youTubeDataApi.searchList(channelId);
        List<String> result = response.videoIds();
        return fetchMoreVideoIdsIfExist(channelId, response.nextPageToken(), result);
    }

    private List<String> fetchMoreVideoIdsIfExist(String channelId, String nextPageToken, List<String> result) {
        if (nextPageToken == null) {
            return result;
        }
        SearchListResponse response = youTubeDataApi.searchList(channelId, nextPageToken);
        result.addAll(response.videoIds());
        return fetchMoreVideoIdsIfExist(channelId, response.nextPageToken(), result);
    }

    @Override
    public List<String> fetchNewByChannelId(String channelId, LocalDateTime startDateTime) {
        SearchListResponse response = youTubeDataApi.searchList(channelId);
        List<String> videoIds = response.afterVideoIds(startDateTime);
        if (hasBeforeItem(response, videoIds)) {
            return videoIds;
        }
        List<String> result = new ArrayList<>(videoIds);
        return fetchMoreNewVideoIdsIfExist(channelId, startDateTime, response.nextPageToken(), result);
    }

    private boolean hasBeforeItem(SearchListResponse response, List<String> videoIds) {
        return response.items().size() > videoIds.size();
    }

    private List<String> fetchMoreNewVideoIdsIfExist(
            String channelId,
            LocalDateTime startDateTime,
            String nextPageToken,
            List<String> result
    ) {
        if (nextPageToken == null) {
            return result;
        }
        SearchListResponse response = youTubeDataApi.searchList(channelId, nextPageToken);
        List<String> videoIds = response.afterVideoIds(startDateTime);
        result.addAll(videoIds);
        if (hasBeforeItem(response, videoIds)) {
            return result;
        }
        return fetchMoreNewVideoIdsIfExist(channelId, startDateTime, response.nextPageToken(), result);
    }
}
