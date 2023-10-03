package com.celuveat.restaurant.query.dto;

import com.celuveat.restaurant.command.domain.SocialMedia;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public record RestaurantByAddressResponse(
        Long id,
        String name,
        String category,
        String roadAddress,
        @JsonProperty("lat") Double latitude,
        @JsonProperty("lng") Double longitude,
        String phoneNumber,
        String naverMapUrl,
        int viewCount,
        int likeCount,
        List<CelebInfo> celebs,
        List<RestaurantImageInfo> images
) {

    public RestaurantByAddressResponse(
            Long id,
            String name,
            String category,
            String roadAddress,
            Double latitude,
            Double longitude,
            String phoneNumber,
            String naverMapUrl,
            int viewCount,
            int likeCount
    ) {
        this(id, name, category, roadAddress,
                latitude, longitude,
                phoneNumber, naverMapUrl,
                viewCount, likeCount,
                new ArrayList<>(),
                new ArrayList<>());
    }

    public void setCelebs(List<CelebInfo> celebs) {
        this.celebs.clear();
        this.celebs.addAll(celebs);
    }

    public void setImages(List<RestaurantImageInfo> images) {
        this.images.clear();
        this.images.addAll(images);
    }

    public record CelebInfo(
            Long restaurantId,
            Long id,
            String name,
            String youtubeChannelName,
            String profileImageUrl
    ) {
    }

    public record RestaurantImageInfo(
            Long restaurantId,
            Long id,
            String name,
            String author,
            String sns
    ) {
        public RestaurantImageInfo(
                Long restaurantId,
                Long id,
                String name,
                String author,
                SocialMedia socialMedia
        ) {
            this(restaurantId, id, name, author, socialMedia.name());
        }
    }
}
