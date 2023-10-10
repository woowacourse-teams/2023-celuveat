package com.celuveat.restaurant.presentation.dto;

import com.celuveat.restaurant.presentation.CategoryMapper;
import com.celuveat.restaurant.query.dao.RestaurantSearchQueryResponseDao.RestaurantSearchCond;

public record RestaurantSearchCondRequest(
        Long celebId,
        String category,
        String restaurantName
) {

    public RestaurantSearchCond toCondition() {
        return new RestaurantSearchCond(
                celebId,
                CategoryMapper.mapCategory(category),
                restaurantName
        );
    }
}
