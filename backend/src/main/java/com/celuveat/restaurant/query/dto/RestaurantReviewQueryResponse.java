package com.celuveat.restaurant.query.dto;

import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import java.util.List;

public record RestaurantReviewQueryResponse(
        Integer totalElementsCount,
        List<RestaurantReviewSingleResponse> reviews
) {

    public static RestaurantReviewQueryResponse from(List<RestaurantReview> restaurantReviews) {
        List<RestaurantReviewSingleResponse> reviews = restaurantReviews.stream()
                .map(RestaurantReviewSingleResponse::from)
                .toList();
        return new RestaurantReviewQueryResponse(reviews.size(), reviews);
    }
}
