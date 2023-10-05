package com.celuveat.restaurant.query.dto;

import com.celuveat.restaurant.command.domain.Restaurant;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

public record RestaurantDetailResponse(
        Long id,
        String name,
        String category,
        String roadAddress,
        @JsonProperty("lat") Double latitude,
        @JsonProperty("lng") Double longitude,
        String phoneNumber,
        String naverMapUrl,
        Integer likeCount,
        Integer viewCount,
        Boolean isLiked,
        Double rating,
        List<CelebQueryResponse> celebs,
        List<RestaurantImageQueryResponse> images
) {

    @Builder
    public RestaurantDetailResponse(
            Restaurant restaurant,
            List<CelebQueryResponse> celebs,
            List<RestaurantImageQueryResponse> restaurantImages,
            int likeCount,
            boolean isLiked
    ) {
        this(restaurant.id(),
                restaurant.name(),
                restaurant.category(),
                restaurant.roadAddress(),
                restaurant.latitude(),
                restaurant.longitude(),
                restaurant.phoneNumber(),
                restaurant.naverMapUrl(),
                likeCount,
                restaurant.viewCount(),
                isLiked,
                restaurant.averageRating(),
                celebs,
                restaurantImages);
    }

    public static RestaurantDetailResponse of(
            RestaurantDetailResponse other,
            List<CelebQueryResponse> celebs,
            List<RestaurantImageQueryResponse> restaurantImages
    ) {
        return new RestaurantDetailResponse(
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
