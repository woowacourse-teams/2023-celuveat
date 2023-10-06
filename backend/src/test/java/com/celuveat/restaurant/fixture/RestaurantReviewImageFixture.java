package com.celuveat.restaurant.fixture;

import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewImage;
import java.util.ArrayList;
import java.util.List;

public class RestaurantReviewImageFixture {

    public static List<RestaurantReviewImage> 리뷰의_사진들(RestaurantReview 리뷰) {
        return 리뷰의_사진들(리뷰, 3);
    }

    public static List<RestaurantReviewImage> 리뷰의_사진들(RestaurantReview 리뷰, int 사진_개수) {
        List<RestaurantReviewImage> restaurantReviewImages = new ArrayList<>();
        for (int i = 1; i <= 사진_개수; i++) {
            restaurantReviewImages.add(new RestaurantReviewImage(리뷰.content() + "_" + i));
        }
        return restaurantReviewImages;
    }
}
