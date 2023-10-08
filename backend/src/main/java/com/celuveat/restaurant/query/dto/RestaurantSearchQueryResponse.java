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
public class RestaurantSearchQueryResponse {

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
    @JsonProperty("isLiked")
    private boolean isLiked;
    private double rating;
    private int distance;
    private List<CelebQueryResponse> celebs = new ArrayList<>();
    private List<RestaurantImageQueryResponse> images = new ArrayList<>();

    public RestaurantSearchQueryResponse(
            Long id, String name,
            String category, String superCategory,
            String roadAddress,
            double latitude, double longitude,
            String phoneNumber, String naverMapUrl,
            int viewCount, int likeCount,
            int reviewCount, double totalRating,
            double distance
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
        this.distance = (int) distance;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public void setCelebs(List<CelebQueryResponse> celebs) {
        if (celebs != null) {
            this.celebs = celebs;
        }
    }

    public void setImages(List<RestaurantImageQueryResponse> images) {
        if (images != null) {
            this.images = images;
        }
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String category() {
        return category;
    }

    public String superCategory() {
        return superCategory;
    }

    public String roadAddress() {
        return roadAddress;
    }

    public double latitude() {
        return latitude;
    }

    public double longitude() {
        return longitude;
    }

    public String phoneNumber() {
        return phoneNumber;
    }

    public String naverMapUrl() {
        return naverMapUrl;
    }

    public int viewCount() {
        return viewCount;
    }

    public int likeCount() {
        return likeCount;
    }

    @JsonProperty("isLiked")
    public boolean isLiked() {
        return isLiked;
    }

    public double rating() {
        return rating;
    }

    public int distance() {
        return distance;
    }

    public List<CelebQueryResponse> celebs() {
        return celebs;
    }

    public List<RestaurantImageQueryResponse> images() {
        return images;
    }
}
