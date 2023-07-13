package com.celuveat.celuveat.celeb.infra.youtube.api;

import static com.celuveat.celuveat.celeb.exception.CelebFetcherExceptionType.NOT_FOUND_CELEB;

import com.celuveat.celuveat.celeb.exception.CelebFetcherException;
import com.celuveat.celuveat.celeb.infra.youtube.dto.channels.ChannelListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;

public class MockYouTubeChannelApi extends YouTubeChannelApi {

    private static final String FILEPATH = "youtube/channels/%s.json";

    private final Map<String, ChannelListResponse> responseByChannelId = new HashMap<>();

    public MockYouTubeChannelApi() {
        super(null, null);
        loadChannelResponse();
    }

    private void loadChannelResponse() {
        Arrays.stream(Channel.values()).forEach(this::loadChannelResponse);
    }

    private void loadChannelResponse(Channel channel) {
        ChannelListResponse response = readFile(channel.filename());
        responseByChannelId.put(channel.channelId(), response);
    }

    private ChannelListResponse readFile(String filename) {
        try {
            File file = new ClassPathResource(String.format(FILEPATH, filename)).getFile();
            return new ObjectMapper().readValue(file, ChannelListResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CelebFetcherException(NOT_FOUND_CELEB);
        }
    }

    @Override
    public ChannelListResponse searchChannelById(String channelId) {
        if (responseByChannelId.containsKey(channelId)) {
            return responseByChannelId.get(channelId);
        }
        throw new CelebFetcherException(NOT_FOUND_CELEB);
    }
}
