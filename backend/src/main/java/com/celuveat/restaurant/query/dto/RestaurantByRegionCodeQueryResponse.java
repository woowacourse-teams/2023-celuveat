package com.celuveat.restaurant.query.dto;

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
    private List<CelebQueryResponse> celebs = new ArrayList<>();
    private List<RestaurantImageQueryResponse> images = new ArrayList<>();

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

    public void setCelebs(List<CelebQueryResponse> celebs) {
        this.celebs = celebs;
    }

    public void setImages(List<RestaurantImageQueryResponse> images) {
        this.images = images;
    }
}
