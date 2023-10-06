package com.celuveat.restaurant.query.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantSearchResponse {

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
    private Integer viewCount;
    private Integer distance;
    private int likeCount;
    @JsonProperty("isLiked")
    private boolean isLiked;
    private Double rating;
    private List<CelebQueryResponse> celebs = new ArrayList<>();
    private List<RestaurantImageQueryResponse> images = new ArrayList<>();

    public RestaurantSearchResponse(
            Long id, String name,
            String category, String superCategory,
            String roadAddress,
            Double latitude, Double longitude,
            String phoneNumber, String naverMapUrl,
            Integer viewCount, Double distance,
            int likeCount, Double rating
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
        this.distance = distance.intValue();
        this.likeCount = likeCount;
        this.rating = rating;
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

    public Double latitude() {
        return latitude;
    }

    public Double longitude() {
        return longitude;
    }

    public String phoneNumber() {
        return phoneNumber;
    }

    public String naverMapUrl() {
        return naverMapUrl;
    }

    public Integer viewCount() {
        return viewCount;
    }

    public Integer distance() {
        return distance;
    }

    public int likeCount() {
        return likeCount;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public Double rating() {
        return rating;
    }

    public List<CelebQueryResponse> celebs() {
        return celebs;
    }

    public List<RestaurantImageQueryResponse> images() {
        return images;
    }
}
