package com.celuveat.restaurant.command.domain;

import static com.celuveat.restaurant.exception.RestaurantExceptionType.NOT_FOUND_RESTAURANT;

import com.celuveat.restaurant.exception.RestaurantException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    default Restaurant getById(Long id) {
        return findById(id).orElseThrow(() ->
                new RestaurantException(NOT_FOUND_RESTAURANT));
    }

    Optional<Restaurant> findByNameAndRoadAddress(String name, String roadAddress);
}
