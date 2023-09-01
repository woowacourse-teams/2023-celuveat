package com.celuveat.restaurant.query;

import static com.celuveat.restaurant.exception.RestaurantExceptionType.NOT_FOUND_RESTAURANT;

import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.exception.RestaurantException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantQueryRepository extends
        JpaRepository<Restaurant, Long>, RestaurantEntityManagerQueryRepository {

    @Override
    default Restaurant getById(Long id) {
        return findById(id).orElseThrow(() ->
                new RestaurantException(NOT_FOUND_RESTAURANT));
    }
}
