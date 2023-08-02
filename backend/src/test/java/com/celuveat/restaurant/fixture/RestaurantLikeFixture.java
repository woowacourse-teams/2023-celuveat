package com.celuveat.restaurant.fixture;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantLike;

public class RestaurantLikeFixture {

    public static RestaurantLike 음식점_좋아요(Restaurant 음식점, OauthMember 멤버) {
        return new RestaurantLike(음식점, 멤버);
    }
}
