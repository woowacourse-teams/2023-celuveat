package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.api;

import com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.dto.search.SearchListResponse;

public interface YouTubeDataApi {

    SearchListResponse searchList(String channelId);

    SearchListResponse searchList(String channelId, String pageToken);
}
