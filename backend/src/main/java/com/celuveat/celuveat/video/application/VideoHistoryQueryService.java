package com.celuveat.celuveat.video.application;

import com.celuveat.celuveat.video.application.dto.FindAllVideoHistoryResponse;
import com.celuveat.celuveat.video.infra.persistence.VideoHistoryQueryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoHistoryQueryService {

    private final VideoHistoryQueryDao videoHistoryQueryDao;

    public List<FindAllVideoHistoryResponse> findAllVideoHistoryResponses() {
        return videoHistoryQueryDao.findAllVideoHistory();
    }
}
