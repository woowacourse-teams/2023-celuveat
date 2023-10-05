package com.celuveat.restaurant.fixture;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;

public class RestaurantReviewFixture {

    public static RestaurantReview 음식점_리뷰(OauthMember member, Restaurant restaurant) {
        return new RestaurantReview(member.nickname() + "," + restaurant.name(), member, restaurant, 5.0, 0);
    }
}
