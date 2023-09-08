package com.celuveat.video.query;

import com.celuveat.video.query.dao.VideoWithCelebQueryResponseDao;
import com.celuveat.video.query.dto.VideoWithCelebQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoQueryService {

    private final VideoWithCelebQueryResponseDao videoWithCelebQueryResponseDao;

    public Page<VideoWithCelebQueryResponse> findAllVideoWithCeleb(
            VideoWithCelebQueryResponseDao.VideoSearchCond videoSearchCond,
            Pageable pageable
    ) {
        return videoWithCelebQueryResponseDao.find(videoSearchCond, pageable);
    }
}
