package com.celuveat.restaurant.command.domain.review;

import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.NOT_FOUND_RESTAURANT_REVIEW;

import com.celuveat.restaurant.exception.RestaurantReviewException;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantReviewRepository extends JpaRepository<RestaurantReview, Long> {

    @EntityGraph(attributePaths = "member")
    List<RestaurantReview> findAllByRestaurantIdOrderByCreatedDateDesc(Long restaurantId);

    default RestaurantReview getById(Long id) {
        return findById(id).orElseThrow(() -> new RestaurantReviewException(NOT_FOUND_RESTAURANT_REVIEW));
    }

    void deleteAllByMemberId(Long memberId);
}
