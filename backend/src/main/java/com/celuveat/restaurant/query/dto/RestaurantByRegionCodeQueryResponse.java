package com.celuveat.restaurant.query.dto;

import com.celuveat.common.util.Base64Util;
import com.celuveat.restaurant.command.domain.SocialMedia;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class RestaurantByRegionCodeQueryResponse {

    private Long id;
    private String name;
    private String category;
    private String superCategory;
    private String roadAddress;
    @JsonProperty("lat")
    private Double latitude;
    @JsonProperty("lng")
    private Double longitude;
    private String phoneNumber;
    private String naverMapUrl;
    private int viewCount;
    private int likeCount;
    @JsonProperty("isLiked")
    private boolean isLiked;
    private List<CelebInfo> celebs = new ArrayList<>();
    private List<RestaurantImageInfo> images = new ArrayList<>();

    public RestaurantByRegionCodeQueryResponse(
            Long id, String name,
            String category, String superCategory,
            String roadAddress,
            Double latitude, Double longitude,
            String phoneNumber, String naverMapUrl,
            int viewCount, int likeCount
    ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.superCategory = superCategory;
        this.roadAddress = roadAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.naverMapUrl = naverMapUrl;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
    }

    public void setLiked(boolean liked) {
        this.isLiked = liked;
    }

    public void setCelebs(List<CelebInfo> celebs) {
        this.celebs = celebs;
    }

    public void setImages(List<RestaurantImageInfo> images) {
        this.images = images;
    }

    // TODO 기존 클래스 사용
    public record CelebInfo(
            Long restaurantId,
            Long id,
            String name,
            String youtubeChannelName,
            String profileImageUrl
    ) {
    }

    // TODO 기존 클래스 사용
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

        @Override
        public String name() {
            return Base64Util.encode(name);
        }
    }
}
