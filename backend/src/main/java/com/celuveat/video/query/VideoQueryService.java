package com.celuveat.video.query;

import com.celuveat.video.query.dao.VideoQueryResponseDao;
import com.celuveat.video.query.dto.VideoQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VideoQueryService {

    private final VideoQueryResponseDao videoQueryResponseDao;

    public Page<VideoQueryResponse> findAllVideoWithCeleb(
            VideoQueryResponseDao.VideoSearchCond videoSearchCond,
            Pageable pageable
    ) {
        return videoQueryResponseDao.find(videoSearchCond, pageable);
    }
}
