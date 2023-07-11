package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.api;

import static com.celuveat.celuveat.restaurantlinkfetcher.exception.RestaurantLinkFetcherExceptionType.NOT_FOUND_RESTAURANT_LINK;

import com.celuveat.celuveat.restaurantlinkfetcher.exception.RestaurantLinkFetcherException;
import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.search.SearchListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;

public class MockYouTubeDataApi extends YouTubeDataApi {

    private final Map<String, Map<String, SearchListResponse>> responsesByChannelId = new HashMap<>();

    public MockYouTubeDataApi() {
        super(null, null);
        loadData();
    }

    private void loadData() {
        Arrays.stream(Channel.values()).forEach(this::loadData);
    }

    private void loadData(Channel channel) {
        String filepath = channel.filepath();
        Map<String, SearchListResponse> responses = new HashMap<>();
        channel.tokenByFilename().forEach((filename, token) -> responses.put(token, loadData(filepath, filename)));
        responsesByChannelId.put(channel.channelId(), responses);
    }

    private SearchListResponse loadData(String filepath, String filename) {
        try {
            File file = new ClassPathResource(String.format(filepath, filename)).getFile();
            return new ObjectMapper().readValue(file, SearchListResponse.class);
        } catch (IOException e) {
            throw new RestaurantLinkFetcherException(NOT_FOUND_RESTAURANT_LINK);
        }
    }

    @Override
    public SearchListResponse searchList(String channelId) {
        if (responsesByChannelId.containsKey(channelId)) {
            return responsesByChannelId.get(channelId).get("CDIQAQ");
        }
        throw new RestaurantLinkFetcherException(NOT_FOUND_RESTAURANT_LINK);
    }

    @Override
    public SearchListResponse searchList(String channelId, String pageToken) {
        if (responsesByChannelId.containsKey(channelId)) {
            return responsesByChannelId.get(channelId).get(pageToken);
        }
        throw new RestaurantLinkFetcherException(NOT_FOUND_RESTAURANT_LINK);
    }
}
