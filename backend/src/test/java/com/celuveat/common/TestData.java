package com.celuveat.common;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.video.command.domain.Video;
import java.util.List;
import java.util.Map;
import lombok.Builder;

@Builder
public record TestData(
        List<Celeb> celebs,
        List<Restaurant> restaurants,
        Map<Restaurant, List<RestaurantImage>> restaurantImages,
        Map<Restaurant, List<Video>> videos
) {
}
