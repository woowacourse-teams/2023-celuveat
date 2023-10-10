package com.celuveat.restaurant.query.dao.support;

import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantReviewQueryDaoSupport extends JpaRepository<RestaurantReview, Long> {

    @EntityGraph(attributePaths = "member")
    List<RestaurantReview> findAllByRestaurantIdOrderByCreatedDateDesc(Long restaurantId);
}
