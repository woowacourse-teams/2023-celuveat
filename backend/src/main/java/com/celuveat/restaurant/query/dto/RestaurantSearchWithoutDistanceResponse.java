package com.celuveat.restaurant.query.dto;

import com.celuveat.common.util.RatingUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantSearchWithoutDistanceResponse {

    private Long id;
    private String name;
    private String category;
    private String superCategory;
    private String roadAddress;
    @JsonProperty("lat")
    private double latitude;
    @JsonProperty("lng")
    private double longitude;
    private String phoneNumber;
    private String naverMapUrl;
    private int viewCount;
    private int likeCount;
    private boolean isLiked;
    private double rating;
    private List<CelebQueryResponse> celebs = new ArrayList<>();
    private List<RestaurantImageQueryResponse> images = new ArrayList<>();

    public RestaurantSearchWithoutDistanceResponse(
            Long id, String name,
            String category, String superCategory,
            String roadAddress,
            double latitude, double longitude,
            String phoneNumber, String naverMapUrl,
            int viewCount, int likeCount,
            int reviewCount, double totalRating
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
        this.rating = RatingUtils.averageRating(totalRating, reviewCount);
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
    @JsonProperty("isLiked")
    public boolean isLiked() {
        return isLiked;
    }
}
