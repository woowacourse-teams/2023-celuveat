package com.celuveat.celuveat.restaurantlinkfetcher.domain;

import java.time.LocalDateTime;
import java.util.List;

public interface RestaurantLinkFetcher {

    List<String> fetchAllByChannelId(String channelId);

    List<String> fetchNewByChannelId(String channelId, LocalDateTime startDateTime);
}
