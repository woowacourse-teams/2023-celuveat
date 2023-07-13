package com.celuveat.celuveat.video.infra.youtube.api;

import static com.celuveat.celuveat.video.exception.VideoHistoryFetcherExceptionType.NOT_FOUND_VIDEO_HISTORY;

import com.celuveat.celuveat.video.exception.VideoHistoryFetcherException;
import com.celuveat.celuveat.video.infra.youtube.dto.search.SearchListResponse;
import com.celuveat.celuveat.video.infra.youtube.dto.video.VideoListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;

public class MockYouTubeDataApi extends YouTubeDataApi {

    private static final String DEFAULT_VIDEO_ID = "8RdkFuFK1DY";
    private static final String VIDEO_FILEPATH = "youtube/videos/tzuyang/tzuyang%s.json";
    private static final List<String> VIDEO_FILENAMES =
            List.of("001", "002", "003", "004", "005", "006", "007", "008", "009", "010");

    private final Map<String, Map<String, SearchListResponse>> responsesByChannelId = new HashMap<>();
    private final Map<String, VideoListResponse> responseByVideoId = new HashMap<>();

    public MockYouTubeDataApi() {
        super(null, null);
        loadSearchResponse();
        loadVideoResponse();
    }

    private void loadSearchResponse() {
        Arrays.stream(Channel.values()).forEach(this::loadSearchResponse);
    }

    private void loadSearchResponse(Channel channel) {
        String filepath = channel.filepath();
        Map<String, SearchListResponse> responses = new HashMap<>();
        channel.tokenByFilename().forEach((filename, token) ->
                responses.put(token, readFile(filepath, filename, SearchListResponse.class))
        );
        responsesByChannelId.put(channel.channelId(), responses);
    }

    private <T> T readFile(String filepath, String filename, Class<T> responseType) {
        try {
            File file = new ClassPathResource(String.format(filepath, filename)).getFile();
            return new ObjectMapper().readValue(file, responseType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new VideoHistoryFetcherException(NOT_FOUND_VIDEO_HISTORY);
        }
    }

    private void loadVideoResponse() {
        VIDEO_FILENAMES.forEach(this::loadVideoResponse);
    }

    private void loadVideoResponse(String filename) {
        VideoListResponse response = readFile(VIDEO_FILEPATH, filename, VideoListResponse.class);
        String videoId = response.items().get(0).id();
        responseByVideoId.put(videoId, response);
    }

    @Override
    public SearchListResponse searchVideosByChannelId(String channelId) {
        if (responsesByChannelId.containsKey(channelId)) {
            return responsesByChannelId.get(channelId).get("CDIQAQ");
        }
        throw new VideoHistoryFetcherException(NOT_FOUND_VIDEO_HISTORY);
    }

    @Override
    public SearchListResponse searchVideosByChannelIdAndPageToken(String channelId, String pageToken) {
        if (responsesByChannelId.containsKey(channelId)) {
            return responsesByChannelId.get(channelId).get(pageToken);
        }
        throw new VideoHistoryFetcherException(NOT_FOUND_VIDEO_HISTORY);
    }

    @Override
    public VideoListResponse searchVideoById(String videoId) {
        if (responseByVideoId.containsKey(videoId)) {
            return responseByVideoId.get(videoId);
        }
        return responseByVideoId.get(DEFAULT_VIDEO_ID);
    }
}
