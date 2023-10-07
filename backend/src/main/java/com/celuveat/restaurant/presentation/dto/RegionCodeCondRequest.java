package com.celuveat.restaurant.presentation.dto;

import com.celuveat.restaurant.query.dao.RestaurantByRegionCodeQueryResponseDao.RegionCodeCond;
import java.util.List;

public record RegionCodeCondRequest(
        List<String> codes
) {

    public RegionCodeCond toCondition() {
        return new RegionCodeCond(codes);
    }
}
