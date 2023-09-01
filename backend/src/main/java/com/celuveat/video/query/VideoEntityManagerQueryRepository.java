package com.celuveat.video.query;

import com.celuveat.video.query.dto.VideoWithCelebQueryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VideoEntityManagerQueryRepository {

    Page<VideoWithCelebQueryResponse> getVideosWithCeleb(
            VideoEntityManagerQueryRepositoryImpl.VideoSearchCond videoSearchCond,
            Pageable pageable
    );
}
