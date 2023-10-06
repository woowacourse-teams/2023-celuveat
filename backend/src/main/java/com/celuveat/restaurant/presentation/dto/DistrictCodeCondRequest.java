package com.celuveat.restaurant.presentation.dto;

import com.celuveat.restaurant.query.dao.RestaurantByAddressResponseDao.DistrictCodeCond;
import java.util.List;

public record DistrictCodeCondRequest(
        List<String> codes
) {

    public DistrictCodeCond toCondition() {
        return new DistrictCodeCond(codes);
    }
}
