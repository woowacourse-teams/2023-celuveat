package com.celuveat.restaurant.query.dto;

import com.celuveat.celeb.domain.Celeb;
import com.celuveat.restaurant.domain.RestaurantImage;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

public record RestaurantSimpleResponse(
        Long id,
        String name,
        String category,
        String roadAddress,
        @JsonProperty("lat") Double latitude,
        @JsonProperty("lng") Double longitude,
        String phoneNumber,
        String naverMapUrl,
        Integer viewCount,
        Integer distance,
        Boolean isLiked,
        Long likeCount,
        List<CelebQueryResponse> celebs,
        List<RestaurantImageQueryResponse> images
) {

    @Builder
    public RestaurantSimpleResponse(
            RestaurantWithDistance restaurant,
            List<Celeb> celebs,
            List<RestaurantImage> restaurantImages,
            boolean isLiked,
            Long likeCount
    ) {
        this(
                restaurant.id(),
                restaurant.name(),
                restaurant.category(),
                restaurant.roadAddress(),
                restaurant.latitude(),
                restaurant.longitude(),
                restaurant.phoneNumber(),
                restaurant.naverMapUrl(),
                restaurant.viewCount(),
                restaurant.distance().intValue(),
                isLiked,
                (likeCount == null) ? 0 : likeCount,
                celebs.stream().map(CelebQueryResponse::of).toList(),
                restaurantImages.stream().map(RestaurantImageQueryResponse::of).toList()
        );
    }

    public static RestaurantSimpleResponse of(
            RestaurantSimpleResponse other,
            List<CelebQueryResponse> celebs,
            List<RestaurantImageQueryResponse> restaurantImages
    ) {
        return new RestaurantSimpleResponse(
                other.id,
                other.name,
                other.category,
                other.roadAddress,
                other.latitude,
                other.longitude,
                other.phoneNumber,
                other.naverMapUrl,
                other.viewCount,
                other.distance,
                other.isLiked,
                other.likeCount,
                celebs,
                restaurantImages
        );
    }
}
