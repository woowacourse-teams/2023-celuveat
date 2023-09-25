package com.celuveat.restaurant.query.dto;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import org.springframework.data.domain.Page;

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

    public static Page<RestaurantSimpleResponse> of(
            Page<RestaurantWithDistance> restaurants,
            Map<Long, List<Celeb>> celebsMap,
            Map<Long, List<RestaurantImage>> restaurantMap,
            Map<Long, Boolean> isLikedMap
    ) {
        return restaurants.map(restaurant ->
                RestaurantSimpleResponse.builder()
                        .restaurant(restaurant)
                        .celebs(celebsMap.get(restaurant.id()))
                        .restaurantImages(restaurantMap.get(restaurant.id()))
                        .isLiked(isLikedMap.get(restaurant.id()))
                        .likeCount(restaurant.likeCount())
                        .build()
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
