package com.celuveat.celuveat.video.application;

import com.celuveat.celuveat.video.application.dto.FindAllVideoByRestaurantIdResponse;
import com.celuveat.celuveat.video.infra.persistence.VideoQueryDao;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoQueryService {

    private final VideoQueryDao videoQueryDao;

    public List<FindAllVideoByRestaurantIdResponse> findAllByRestaurantId(Long restaurantId) {
        return videoQueryDao.findAllByRestaurantId(restaurantId);
    }
}
