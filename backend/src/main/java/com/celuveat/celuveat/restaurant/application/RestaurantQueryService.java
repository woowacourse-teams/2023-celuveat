package com.celuveat.celuveat.restaurant.application;

import com.celuveat.celuveat.restaurant.domain.Restaurant;
import com.celuveat.celuveat.restaurant.infra.persistence.RestaurantDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantQueryService {

    private final RestaurantDao restaurantDao;

    public Restaurant findById(Long id) {
        return restaurantDao.getById(id);
    }
}
