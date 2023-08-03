package com.celuveat.video.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findAllByRestaurantIdIn(List<Long> restaurantIds);
}
