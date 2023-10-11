package com.celuveat.restaurant.query.dto;

import com.celuveat.common.util.RatingUtils;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

public record RestaurantDetailQueryResponse(
        Long id,
        String name,
        String category,
        String roadAddress,
        @JsonProperty("lat") double latitude,
        @JsonProperty("lng") double longitude,
        String phoneNumber,
        String naverMapUrl,
        int likeCount,
        int viewCount,
        boolean isLiked,
        double rating,
        List<CelebQueryResponse> celebs,
        List<RestaurantImageQueryResponse> images
) {

    @Builder
    public RestaurantDetailQueryResponse(
            Restaurant restaurant,
            boolean isLiked,
            List<CelebQueryResponse> celebs,
            List<RestaurantImageQueryResponse> restaurantImages
    ) {
        this(restaurant.id(),
                restaurant.name(),
                restaurant.category(),
                restaurant.roadAddress(),
                restaurant.latitude(),
                restaurant.longitude(),
                restaurant.phoneNumber(),
                restaurant.naverMapUrl(),
                restaurant.likeCount(),
                restaurant.viewCount(),
                isLiked,
                RatingUtils.averageRating(restaurant.totalRating(), restaurant.reviewCount()),
                celebs,
                restaurantImages);
    }

    public static RestaurantDetailQueryResponse of(
            RestaurantDetailQueryResponse other,
            List<CelebQueryResponse> celebs,
            List<RestaurantImageQueryResponse> restaurantImages
    ) {
        return new RestaurantDetailQueryResponse(
                other.id(),
                other.name(),
                other.category(),
                other.roadAddress(),
                other.latitude(),
                other.longitude(),
                other.phoneNumber(),
                other.naverMapUrl(),
                other.likeCount(),
                other.viewCount(),
                other.isLiked(),
                other.rating(),
                celebs,
                restaurantImages
        );
    }
}
