package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.api;

import com.celuveat.celuveat.restaurantlinkfetcher.exception.RestaurantLinkFetcherException;
import com.celuveat.celuveat.restaurantlinkfetcher.exception.RestaurantLinkFetcherExceptionType;
import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.search.SearchListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;

public class MockYouTubeDataApi extends YouTubeDataApi {

    private static final String FILE_PATH = "youtube/search/tzuyang/tzuyang%s.json";
    private static final Map<String, String> TOKEN_BY_FILENAME = Map.of(
            "01", "CDIQAQ",
            "02", "CDIQAA",
            "03", "CGQQAA",
            "04", "CJYBEAA",
            "05", "CMgBEAA",
            "06", "CPoBEAA",
            "07", "CKwCEAA",
            "08", "CN4CEAA",
            "09", "CJADEAA"
    );

    private final Map<String, SearchListResponse> responses = new HashMap<>();

    public MockYouTubeDataApi() {
        super(null, null);
        loadData();
    }

    private void loadData() {
        TOKEN_BY_FILENAME.keySet()
                .forEach(filename -> responses.put(TOKEN_BY_FILENAME.get(filename), loadData(filename)));
    }

    private SearchListResponse loadData(String filename) {
        try {
            File file = new ClassPathResource(String.format(FILE_PATH, filename)).getFile();
            return new ObjectMapper().readValue(file, SearchListResponse.class);
        } catch (IOException e) {
            throw new RestaurantLinkFetcherException(RestaurantLinkFetcherExceptionType.NOT_FOUND_RESTAURANT_LINK);
        }
    }

    @Override
    public SearchListResponse searchList(String channelId) {
        return responses.get("CDIQAQ");
    }

    @Override
    public SearchListResponse searchList(String channelId, String pageToken) {
        return responses.get(pageToken);
    }
}
