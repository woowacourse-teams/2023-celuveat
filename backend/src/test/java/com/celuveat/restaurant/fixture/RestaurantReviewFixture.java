package com.celuveat.restaurant.fixture;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;

public class RestaurantReviewFixture {

    public static RestaurantReview 음식점_리뷰(OauthMember member, Restaurant restaurant) {
        return new RestaurantReview(member.nickname() + "," + restaurant.name(), member, restaurant, 5.0);
    }

    public static RestaurantReview 음식점_1점_리뷰(OauthMember member, Restaurant restaurant) {
        return new RestaurantReview("음식이 최악이에요", member, restaurant, 1.0);
    }

    public static RestaurantReview 음식점_2점_리뷰(OauthMember member, Restaurant restaurant) {
        return new RestaurantReview("음식이 조금 별로에요", member, restaurant, 2.0);
    }

    public static RestaurantReview 음식점_3점_리뷰(OauthMember member, Restaurant restaurant) {
        return new RestaurantReview("평범해요", member, restaurant, 3.0);
    }

    public static RestaurantReview 음식점_4점_리뷰(OauthMember member, Restaurant restaurant) {
        return new RestaurantReview("맛있어요", member, restaurant, 4.0);
    }

    public static RestaurantReview 음식점_5점_리뷰(OauthMember member, Restaurant restaurant) {
        return new RestaurantReview("진짜 짱맛있어요 :)", member, restaurant, 5.0);
    }
}
