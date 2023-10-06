package com.celuveat.restaurant.query.dao.support;

import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantReviewImageQueryDaoSupport extends JpaRepository<RestaurantReviewImage, Long> {

    List<RestaurantReviewImage> findAllByRestaurantReviewIn(List<RestaurantReview> reviews);
}
