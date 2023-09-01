package com.celuveat.restaurant.query;

import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantImageQueryRepository extends JpaRepository<RestaurantImage, Long> {

    List<RestaurantImage> findAllByRestaurantIdIn(List<Long> restaurantIds);

    List<RestaurantImage> findAllByRestaurant(Restaurant restaurant);
}
