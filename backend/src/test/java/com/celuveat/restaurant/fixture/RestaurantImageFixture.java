package com.celuveat.restaurant.fixture;

import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.command.domain.SocialMedia;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantImageFixture {

    public static RestaurantImage 음식점사진(String name, Restaurant 음식점, String author) {
        return RestaurantImage.builder()
                .name(name)
                .socialMedia(SocialMedia.INSTAGRAM)
                .author(author)
                .restaurant(음식점)
                .build();
    }

    public static Map<Restaurant, List<RestaurantImage>> 음식점사진들(List<Restaurant> 음식점들) {
        Map<Restaurant, List<RestaurantImage>> restaurantImageMap = new HashMap<>();
        for (Restaurant restaurant : 음식점들) {
            List<RestaurantImage> restaurantImages = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                restaurantImages.add(음식점사진(restaurant.name() + "_" + i, restaurant, "author"));
            }
            restaurantImageMap.put(restaurant, restaurantImages);
        }
        return restaurantImageMap;
    }
}
