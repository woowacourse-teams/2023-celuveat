package com.celuveat.restaurant.query.dto;

import com.celuveat.celeb.domain.Celeb;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantImage;
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
        List<CelebQueryResponse> celebs,
        List<RestaurantImageQueryResponse> images
) {

    @Builder
    public RestaurantDetailResponse(
            Restaurant restaurant,
            List<Celeb> celebs,
            List<RestaurantImage> restaurantImages,
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
                celebs.stream().map(CelebQueryResponse::of).toList(),
                restaurantImages.stream().map(RestaurantImageQueryResponse::of).toList());
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
                celebs,
                restaurantImages
        );
    }
}
