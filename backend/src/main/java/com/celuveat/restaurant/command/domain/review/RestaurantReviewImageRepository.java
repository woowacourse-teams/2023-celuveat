package com.celuveat.restaurant.command.domain.review;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantReviewImageRepository extends JpaRepository<RestaurantReviewImage, Long> {

    List<RestaurantReviewImage> findRestaurantReviewImagesByRestaurantReview(RestaurantReview review);
}