package com.celuveat.restaurant.query.dto;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewImage;
import java.util.List;

public record RestaurantReviewSingleQueryResponse(
        Long id,
        Long memberId,
        String nickname,
        String profileImageUrl,
        String content,
        String createdDate,
        Integer likeCount,
        Boolean isLiked,
        Double rating,
        List<String> images
) {

    public static RestaurantReviewSingleQueryResponse of(
            RestaurantReview restaurantReview,
            List<RestaurantReviewImage> images,
            boolean isLiked
    ) {
        OauthMember oauthMember = restaurantReview.member();
        return new RestaurantReviewSingleQueryResponse(
                restaurantReview.id(),
                oauthMember.id(),
                oauthMember.nickname(),
                oauthMember.profileImageUrl(),
                restaurantReview.content(),
                restaurantReview.createdDate().toLocalDate().toString(),
                restaurantReview.likeCount(),
                isLiked,
                restaurantReview.rating(),
                images.stream().map(RestaurantReviewImage::name).toList()
        );
    }
}
