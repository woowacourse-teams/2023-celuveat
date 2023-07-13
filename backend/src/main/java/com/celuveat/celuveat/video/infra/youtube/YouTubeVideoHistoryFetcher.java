package com.celuveat.celuveat.video.infra.youtube;

import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.video.domain.VideoHistory;
import com.celuveat.celuveat.video.domain.VideoHistoryFetcher;
import com.celuveat.celuveat.video.infra.youtube.api.YouTubeVideoApi;
import com.celuveat.celuveat.video.infra.youtube.dto.search.SearchListResponse;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class YouTubeVideoHistoryFetcher implements VideoHistoryFetcher {

    private final YouTubeVideoApi youTubeVideoApi;

    @Override
    public List<VideoHistory> fetchAllVideoHistoriesByCeleb(Celeb celeb) {
        String channelId = celeb.youtubeChannelId();
        SearchListResponse response = youTubeVideoApi.searchVideosByChannelId(channelId);
        List<String> videoIds = response.videoIds();
        List<String> more = fetchMoreVideoIdsIfExist(channelId, response.nextPageToken());
        videoIds.addAll(more);
        return fetchAllVideoHistories(videoIds, celeb);
    }

    private List<String> fetchMoreVideoIdsIfExist(String channelId, String nextPageToken) {
        if (nextPageToken == null) {
            return Collections.emptyList();
        }
        SearchListResponse response = youTubeVideoApi.searchVideosByChannelIdAndPageToken(channelId, nextPageToken);
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
        return youTubeVideoApi.searchVideoById(videoId).toVideoHistory(celeb);
    }

    @Override
    public List<VideoHistory> fetchVideoHistoriesByCelebAfterDateTime(Celeb celeb, LocalDateTime startDateTime) {
        String channelId = celeb.youtubeChannelId();
        SearchListResponse response = youTubeVideoApi.searchVideosByChannelId(channelId);
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
        SearchListResponse response = youTubeVideoApi.searchVideosByChannelIdAndPageToken(channelId, nextPageToken);
        List<String> videoIds = response.afterVideoIds(startDateTime);
        if (response.hasBeforeVideo(videoIds)) {
            return videoIds;
        }
        List<String> more = fetchMoreAfterVideoIdsIfExist(channelId, startDateTime, response.nextPageToken());
        videoIds.addAll(more);
        return videoIds;
    }
}
