package com.celuveat.restaurant.query.dto;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.Builder;

public record RestaurantLikeQueryResponse(
        Long id,
        String name,
        String category,
        String roadAddress,
        @JsonProperty("lat") Double latitude,
        @JsonProperty("lng") Double longitude,
        String phoneNumber,
        String naverMapUrl,
        List<CelebQueryResponse> celebs,
        List<RestaurantImageQueryResponse> images
) {

    public static RestaurantLikeQueryResponse of(
            Restaurant restaurant,
            Map<Restaurant, List<Celeb>> celebsMap,
            Map<Restaurant, List<RestaurantImage>> restaurantMap
    ) {
        return RestaurantLikeQueryResponse.builder()
                .restaurant(restaurant)
                .celebs(celebsMap.get(restaurant))
                .restaurantImages(restaurantMap.get(restaurant))
                .build();
    }

    @Builder
    public RestaurantLikeQueryResponse(
            Restaurant restaurant,
            List<Celeb> celebs,
            List<RestaurantImage> restaurantImages
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
                celebs.stream().map(CelebQueryResponse::of).toList(),
                restaurantImages.stream().map(RestaurantImageQueryResponse::of).toList()
        );
    }
}
