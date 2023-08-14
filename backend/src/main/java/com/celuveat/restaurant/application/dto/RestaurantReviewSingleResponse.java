package com.celuveat.restaurant.application.dto;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.restaurant.domain.review.RestaurantReview;

public record RestaurantReviewSingleResponse(
        Long id,
        String nickname,
        String profileImageUrl,
        String content,
        String createdDate
) {

    public static RestaurantReviewSingleResponse from(RestaurantReview restaurantReview) {
        OauthMember oauthMember = restaurantReview.oauthMember();
        return new RestaurantReviewSingleResponse(
                restaurantReview.id(),
                oauthMember.nickname(),
                oauthMember.profileImageUrl(),
                restaurantReview.content(),
                restaurantReview.createdDate().toLocalDate().toString()
        );
    }
}
