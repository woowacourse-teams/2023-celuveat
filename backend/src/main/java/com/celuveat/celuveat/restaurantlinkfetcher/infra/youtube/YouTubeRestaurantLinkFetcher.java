package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube;

import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.restaurantlinkfetcher.domain.RestaurantLinkFetcher;
import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.api.YouTubeDataApi;
import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.search.SearchListResponse;
import com.celuveat.celuveat.video.domain.VideoHistory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class YouTubeRestaurantLinkFetcher implements RestaurantLinkFetcher {

    private final YouTubeDataApi youTubeDataApi;

    @Override
    public List<VideoHistory> fetchAllByCeleb(Celeb celeb) {
        String channelId = celeb.youtubeChannelId();
        SearchListResponse response = youTubeDataApi.searchList(channelId);
        List<String> videoIds = response.videoIds();
        fetchMoreVideoIdsIfExist(channelId, response.nextPageToken(), videoIds);
        return fetchAllVideoHistories(videoIds, celeb);
    }

    private void fetchMoreVideoIdsIfExist(String channelId, String nextPageToken, List<String> result) {
        if (nextPageToken == null) {
            return;
        }
        SearchListResponse response = youTubeDataApi.searchList(channelId, nextPageToken);
        result.addAll(response.videoIds());
        fetchMoreVideoIdsIfExist(channelId, response.nextPageToken(), result);
    }

    private List<VideoHistory> fetchAllVideoHistories(List<String> videoIds, Celeb celeb) {
        return videoIds.stream()
                .map(videoId -> fetchVideoHistoryById(videoId, celeb))
                .toList();
    }

    private VideoHistory fetchVideoHistoryById(String videoId, Celeb celeb) {
        return youTubeDataApi.searchVideoById(videoId).toVideoHistory(celeb);
    }

    @Override
    public List<VideoHistory> fetchNewByCeleb(Celeb celeb, LocalDateTime startDateTime) {
        String channelId = celeb.youtubeChannelId();
        SearchListResponse response = youTubeDataApi.searchList(channelId);
        List<String> videoIds = response.afterVideoIds(startDateTime);
        if (response.isAllAfterVideo(videoIds)) {
            fetchMoreNewVideoIdsIfExist(channelId, startDateTime, response.nextPageToken(), videoIds);
        }
        return fetchAllVideoHistories(videoIds, celeb);
    }

    private void fetchMoreNewVideoIdsIfExist(
            String channelId,
            LocalDateTime startDateTime,
            String nextPageToken,
            List<String> result
    ) {
        if (nextPageToken == null) {
            return;
        }
        SearchListResponse response = youTubeDataApi.searchList(channelId, nextPageToken);
        List<String> videoIds = response.afterVideoIds(startDateTime);
        result.addAll(videoIds);
        if (response.hasBeforeVideo(videoIds)) {
            return;
        }
        fetchMoreNewVideoIdsIfExist(channelId, startDateTime, response.nextPageToken(), result);
    }
}
