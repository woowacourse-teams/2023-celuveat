package com.celuveat.restaurant.query.dto;

import com.celuveat.common.util.RatingUtils;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

// TODO 여기에 조회수, 평점 등에 대한 정보가 없는데 이대로 갈건지?, 만약 변경된다면 해당 클래스 대신 RestaurantWithoutDistanceQueryResponse를 사용할 것
public record LikedRestaurantQueryResponse(
        Long id,
        String name,
        String category,
        String superCategory,
        String roadAddress,
        @JsonProperty("lat") double latitude,
        @JsonProperty("lng") double longitude,
        String phoneNumber,
        String naverMapUrl,
        int viewCount,
        int likeCount,
        double rating,
        List<CelebQueryResponse> celebs,
        List<RestaurantImageQueryResponse> images
) {

    public static LikedRestaurantQueryResponse from(
            Restaurant restaurant,
            List<CelebQueryResponse> celebs,
            List<RestaurantImageQueryResponse> images
    ) {
        return new LikedRestaurantQueryResponse(
                restaurant.id(),
                restaurant.name(),
                restaurant.category(),
                restaurant.superCategory(),
                restaurant.roadAddress(),
                restaurant.latitude(),
                restaurant.longitude(),
                restaurant.phoneNumber(),
                restaurant.naverMapUrl(),
                restaurant.viewCount(),
                restaurant.likeCount(),
                RatingUtils.averageRating(restaurant.totalRating(), restaurant.reviewCount()),
                celebs,
                images
        );
    }
}
