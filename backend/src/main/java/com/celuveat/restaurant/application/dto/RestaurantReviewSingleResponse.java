package com.celuveat.restaurant.application.dto;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.restaurant.domain.review.RestaurantReview;

public record RestaurantReviewSingleResponse(
        Long id,
        Long memberId,
        String nickname,
        String profileImageUrl,
        String content,
        String createdDate
) {

    public static RestaurantReviewSingleResponse from(RestaurantReview restaurantReview) {
        OauthMember oauthMember = restaurantReview.oauthMember();
        return new RestaurantReviewSingleResponse(
                restaurantReview.id(),
                oauthMember.id(),
                oauthMember.nickname(),
                oauthMember.profileImageUrl(),
                restaurantReview.content(),
                restaurantReview.createdDate().toLocalDate().toString()
        );
    }
}
