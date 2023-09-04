package com.celuveat.restaurant.command.domain.review;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantReviewReportRepository extends JpaRepository<RestaurantReviewReport, Long> {

    List<RestaurantReviewReport> findAllByRestaurantReviewId(Long restaurantReviewId);
}
