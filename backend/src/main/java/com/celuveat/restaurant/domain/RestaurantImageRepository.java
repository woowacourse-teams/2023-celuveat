package com.celuveat.restaurant.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantImageRepository extends JpaRepository<RestaurantImage, Long> {

    List<RestaurantImage> findAllByRestaurantIn(List<Restaurant> restaurants);
}
