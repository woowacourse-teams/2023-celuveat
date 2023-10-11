package com.celuveat.restaurant.fixture;

import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantRecommendation;

public class RestaurantRecommendationFixture {

    public static RestaurantRecommendation 추천_음식점(Restaurant 음식점) {
        return new RestaurantRecommendation(음식점);
    }
}
