package com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube;

import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.restaurantvideohistoryfetcher.domain.VideoHistoryFetcher;
import com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube.api.YouTubeDataApi;
import com.celuveat.celuveat.restaurantvideohistoryfetcher.infra.youtube.dto.search.SearchListResponse;
import com.celuveat.celuveat.video.domain.VideoHistory;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class YouTubeVideoHistoryFetcher implements VideoHistoryFetcher {

    private final YouTubeDataApi youTubeDataApi;

    @Override
    public List<VideoHistory> fetchAllVideoHistoriesByCeleb(Celeb celeb) {
        String channelId = celeb.youtubeChannelId();
        SearchListResponse response = youTubeDataApi.searchVideosByChannelId(channelId);
        List<String> videoIds = response.videoIds();
        List<String> more = fetchMoreVideoIdsIfExist(channelId, response.nextPageToken());
        videoIds.addAll(more);
        return fetchAllVideoHistories(videoIds, celeb);
    }

    private List<String> fetchMoreVideoIdsIfExist(String channelId, String nextPageToken) {
        if (nextPageToken == null) {
            return Collections.emptyList();
        }
        SearchListResponse response = youTubeDataApi.searchVideosByChannelIdAndPageToken(channelId, nextPageToken);
        List<String> videoIds = response.videoIds();
        List<String> more = fetchMoreVideoIdsIfExist(channelId, response.nextPageToken());
        videoIds.addAll(more);
        return videoIds;
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
    public List<VideoHistory> fetchVideoHistoriesByCelebAfterDateTime(Celeb celeb, LocalDateTime startDateTime) {
        String channelId = celeb.youtubeChannelId();
        SearchListResponse response = youTubeDataApi.searchVideosByChannelId(channelId);
        List<String> videoIds = response.afterVideoIds(startDateTime);
        if (response.isAllAfterVideo(videoIds)) {
            List<String> more = fetchMoreAfterVideoIdsIfExist(channelId, startDateTime, response.nextPageToken());
            videoIds.addAll(more);
        }
        return fetchAllVideoHistories(videoIds, celeb);
    }

    private List<String> fetchMoreAfterVideoIdsIfExist(
            String channelId,
            LocalDateTime startDateTime,
            String nextPageToken
    ) {
        if (nextPageToken == null) {
            return Collections.emptyList();
        }
        SearchListResponse response = youTubeDataApi.searchVideosByChannelIdAndPageToken(channelId, nextPageToken);
        List<String> videoIds = response.afterVideoIds(startDateTime);
        if (response.hasBeforeVideo(videoIds)) {
            return videoIds;
        }
        List<String> more = fetchMoreAfterVideoIdsIfExist(channelId, startDateTime, response.nextPageToken());
        videoIds.addAll(more);
        return videoIds;
    }
}
