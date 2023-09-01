package com.celuveat.restaurant.query;

import com.celuveat.restaurant.query.RestaurantEntityManagerQueryRepositoryImpl.LocationSearchCond;
import com.celuveat.restaurant.query.RestaurantEntityManagerQueryRepositoryImpl.RestaurantSearchCond;
import com.celuveat.restaurant.query.dto.RestaurantWithDistance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantEntityManagerQueryRepository {

    Page<RestaurantWithDistance> getRestaurantsWithDistance(
            RestaurantSearchCond restaurantSearchCond,
            LocationSearchCond locationSearchCond,
            Pageable pageable
    );

    Page<RestaurantWithDistance> getRestaurantsNearByRestaurantId(
            int distance,
            Long restaurantId,
            Pageable pageable
    );
}
