package com.celuveat.restaurant.application.dto;

import com.celuveat.celeb.domain.Celeb;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantImage;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

public record RestaurantWithCelebAndImagesDetailResponse(
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
        List<CelebQueryResponse> celebs,
        List<RestaurantImageQueryResponse> images
) {

    @Builder
    public RestaurantWithCelebAndImagesDetailResponse(
            Restaurant restaurant,
            List<Celeb> celebs,
            List<RestaurantImage> restaurantImages,
            int likeCount
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
                celebs.stream().map(CelebQueryResponse::of).toList(),
                restaurantImages.stream().map(RestaurantImageQueryResponse::of).toList());
    }

    public static RestaurantWithCelebAndImagesDetailResponse of(
            Restaurant restaurant,
            List<Celeb> celebs,
            List<RestaurantImage> restaurantImages,
            int likeCount
    ) {
        return new RestaurantWithCelebAndImagesDetailResponse(
                restaurant.id(),
                restaurant.name(),
                restaurant.category(),
                restaurant.roadAddress(),
                restaurant.latitude(),
                restaurant.longitude(),
                restaurant.phoneNumber(),
                restaurant.naverMapUrl(),
                likeCount,
                restaurant.viewCount(),
                celebs.stream().map(CelebQueryResponse::of).toList(),
                restaurantImages.stream().map(RestaurantImageQueryResponse::of).toList()
        );
    }

    public static RestaurantWithCelebAndImagesDetailResponse of(
            RestaurantWithCelebAndImagesDetailResponse other,
            List<CelebQueryResponse> celebs,
            List<RestaurantImageQueryResponse> restaurantImages
    ) {
        return new RestaurantWithCelebAndImagesDetailResponse(
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
                celebs,
                restaurantImages
        );
    }
}
