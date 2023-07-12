package com.celuveat.celuveat.restaurantlinkfetcher.domain;

import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.video.domain.VideoHistory;
import java.time.LocalDateTime;
import java.util.List;

public interface RestaurantLinkFetcher {

    List<VideoHistory> fetchAllByCeleb(Celeb celeb);

    List<VideoHistory> fetchNewByCeleb(Celeb celeb, LocalDateTime startDateTime);
}
