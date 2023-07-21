package com.celuveat.video.domain;

import com.celuveat.restaurant.domain.Restaurant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findAllByRestaurantIn(List<Restaurant> restaurants);
}
