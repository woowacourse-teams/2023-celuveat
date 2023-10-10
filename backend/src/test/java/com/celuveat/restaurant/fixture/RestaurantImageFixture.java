package com.celuveat.restaurant.fixture;

import static com.celuveat.restaurant.command.domain.SocialMedia.INSTAGRAM;
import static com.celuveat.restaurant.command.domain.SocialMedia.YOUTUBE;

import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import java.util.ArrayList;
import java.util.List;

public class RestaurantImageFixture {

    public static RestaurantImage 모던샤브하우스_사진(Restaurant 모던샤브하우스, int 번호) {
        return RestaurantImage.builder()
                .name("liwoo_모던샤브하우스_" + 번호)
                .author("@Liwoo_foodie")
                .socialMedia(YOUTUBE)
                .restaurant(모던샤브하우스)
                .build();
    }

    public static RestaurantImage 대성집_사진(Restaurant 대성집, int 번호) {
        return RestaurantImage.builder()
                .name("k_hyo_l_대성집_" + 번호)
                .author("@k_hyo_l")
                .socialMedia(INSTAGRAM)
                .restaurant(대성집)
                .build();
    }

    public static RestaurantImage 하늘초밥_사진(Restaurant 하늘초밥, int 번호) {
        return RestaurantImage.builder()
                .name("RawFishEater_하늘초밥_" + 번호)
                .author("@RawFishEater")
                .socialMedia(YOUTUBE)
                .restaurant(하늘초밥)
                .build();
    }

    public static RestaurantImage 음식점사진(String 사진이름, Restaurant 음식점, String 사진_제공자) {
        return RestaurantImage.builder()
                .name(사진이름)
                .socialMedia(INSTAGRAM)
                .author(사진_제공자)
                .restaurant(음식점)
                .build();
    }

    public static List<RestaurantImage> 음식점_사진들(List<Restaurant> 음식점들) {
        return 음식점_사진들(음식점들, 3);
    }

    public static List<RestaurantImage> 음식점_사진들(List<Restaurant> 음식점들, int 사진_개수) {
        List<RestaurantImage> restaurantImages = new ArrayList<>();
        for (Restaurant restaurant : 음식점들) {
            for (int i = 1; i <= 사진_개수; i++) {
                restaurantImages.add(음식점사진(restaurant.name() + "_" + i, restaurant, "사진 제공자"));
            }
        }
        return restaurantImages;
    }
}
