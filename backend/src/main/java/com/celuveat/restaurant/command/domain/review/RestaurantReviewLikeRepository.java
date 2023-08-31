package com.celuveat.restaurant.command.domain.review;

import com.celuveat.auth.command.domain.OauthMember;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantReviewLikeRepository extends JpaRepository<RestaurantReviewLike, Long> {

    Optional<RestaurantReviewLike> findByRestaurantReviewAndMember(
            RestaurantReview restaurantReview,
            OauthMember member
    );
}
