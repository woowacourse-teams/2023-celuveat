package com.celuveat.restaurant.command.application;

import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
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
