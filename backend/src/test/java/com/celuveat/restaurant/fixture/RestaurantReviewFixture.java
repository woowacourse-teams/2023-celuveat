package com.celuveat.restaurant.fixture;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.review.RestaurantReview;

public class RestaurantReviewFixture {

    public static RestaurantReview 음식점_리뷰(OauthMember member, Restaurant restaurant) {
        return new RestaurantReview(member.nickname() + "," + restaurant.name(), member, restaurant);
    }
}
