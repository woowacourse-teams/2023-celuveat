package com.celuveat.restaurant.fixture;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantLike;

public class RestaurantLikeFixture {

    public static RestaurantLike 음식점_좋아요(Restaurant 음식점, OauthMember 멤버) {
        return new RestaurantLike(음식점, 멤버);
    }
}
