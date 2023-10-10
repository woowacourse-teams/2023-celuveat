package com.celuveat.restaurant.command.domain.review;

import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.NOT_FOUND_RESTAURANT_REVIEW;

import com.celuveat.restaurant.exception.RestaurantReviewException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantReviewRepository extends JpaRepository<RestaurantReview, Long> {

    default RestaurantReview getById(Long id) {
        return findById(id).orElseThrow(() -> new RestaurantReviewException(NOT_FOUND_RESTAURANT_REVIEW));
    }

    void deleteAllByMemberId(Long memberId);
}
