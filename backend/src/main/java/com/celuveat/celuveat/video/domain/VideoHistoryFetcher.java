package com.celuveat.celuveat.video.domain;

import com.celuveat.celuveat.celeb.domain.Celeb;
import java.time.LocalDateTime;
import java.util.List;

public interface VideoHistoryFetcher {

    List<VideoHistory> fetchAllVideoHistoriesByCeleb(Celeb celeb);

    List<VideoHistory> fetchVideoHistoriesByCelebAfterDateTime(Celeb celeb, LocalDateTime startDateTime);
}
