package com.celuveat.restaurant.query.dao;

import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantImageQueryDaoSupport extends JpaRepository<RestaurantImage, Long> {

    List<RestaurantImage> findAllByRestaurantIdIn(List<Long> restaurantIds);

    List<RestaurantImage> findAllByRestaurant(Restaurant restaurant);
}
