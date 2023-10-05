package com.celuveat.restaurant.query.dto;

import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewImage;
import java.util.List;
import java.util.Map;

public record RestaurantReviewQueryResponse(
        Integer totalElementsCount,
        List<RestaurantReviewSingleResponse> reviews
) {

    public static RestaurantReviewQueryResponse from(
            List<RestaurantReview> restaurantReviews,
            Map<Long, List<RestaurantReviewImage>> restaurantReviewImages
    ) {
        List<RestaurantReviewSingleResponse> reviews = restaurantReviews.stream()
                .map(review -> RestaurantReviewSingleResponse.of(
                        review,
                        restaurantReviewImages.getOrDefault(review.id(), List.of()))
                )
                .toList();
        return new RestaurantReviewQueryResponse(reviews.size(), reviews);
    }
}
