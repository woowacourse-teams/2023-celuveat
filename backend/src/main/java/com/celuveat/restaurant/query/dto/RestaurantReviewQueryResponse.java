package com.celuveat.restaurant.query.dto;

import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewImage;
import java.util.List;
import java.util.Map;

public record RestaurantReviewQueryResponse(
        Integer totalElementsCount,
        List<RestaurantReviewSingleQueryResponse> reviews
) {

    public static RestaurantReviewQueryResponse from(
            List<RestaurantReview> restaurantReviews,
            Map<Long, List<RestaurantReviewImage>> restaurantReviewImages,
            Map<Long, Boolean> isLikedByReviewId
    ) {
        List<RestaurantReviewSingleQueryResponse> reviews = restaurantReviews.stream()
                .map(review -> RestaurantReviewSingleQueryResponse.of(
                        review,
                        restaurantReviewImages.getOrDefault(review.id(), List.of()),
                        isLikedByReviewId.getOrDefault(review.id(), false)
                )).toList();
        return new RestaurantReviewQueryResponse(reviews.size(), reviews);
    }
}
