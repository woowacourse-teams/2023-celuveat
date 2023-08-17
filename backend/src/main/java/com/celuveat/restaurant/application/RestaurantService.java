package com.celuveat.restaurant.application;

import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public void increaseViewCount(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        restaurant.increaseViewCount();
    }
}
