package com.celuveat.video.application;

import com.celuveat.video.application.dto.VideoWithCelebQueryResponse;
import com.celuveat.video.domain.VideoQueryRepository;
import com.celuveat.video.domain.VideoQueryRepository.VideoSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoQueryService {

    private final VideoQueryRepository videoQueryRepository;

    public Page<VideoWithCelebQueryResponse> findAllVideoWithCeleb(
            VideoSearchCond videoSearchCond,
            Pageable pageable
    ) {
        return videoQueryRepository.getVideosWithCeleb(
                videoSearchCond, pageable
        );
    }
}
