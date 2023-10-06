package com.celuveat.restaurant.query.dao.support;

import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantReviewLikeQueryDaoSupport extends JpaRepository<RestaurantReviewLike, Long> {

    Optional<RestaurantReviewLike> findByRestaurantReviewAndMemberId(RestaurantReview restaurantReview, Long memberId);
}
