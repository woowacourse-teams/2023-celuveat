package com.celuveat.video.presentation.dto;

import com.celuveat.video.query.dao.VideoQueryResponseDao.VideoSearchCond;

public record VideoSearchCondRequest(
        Long celebId,
        Long restaurantId
) {

    public VideoSearchCond toCondition() {
        return new VideoSearchCond(celebId, restaurantId);
    }
}
