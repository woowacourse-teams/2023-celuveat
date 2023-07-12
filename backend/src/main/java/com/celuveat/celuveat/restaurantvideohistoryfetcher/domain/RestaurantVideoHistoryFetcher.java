package com.celuveat.celuveat.restaurantvideohistoryfetcher.domain;

import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.video.domain.VideoHistory;
import java.time.LocalDateTime;
import java.util.List;

public interface RestaurantVideoHistoryFetcher {

    List<VideoHistory> fetchAllVideoHistoriesByCeleb(Celeb celeb);

    List<VideoHistory> fetchVideoHistoriesByCelebAfterDateTime(Celeb celeb, LocalDateTime startDateTime);
}
