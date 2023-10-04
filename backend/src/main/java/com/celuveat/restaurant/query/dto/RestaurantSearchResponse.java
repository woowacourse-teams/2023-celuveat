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
    private boolean isLiked;
    private List<CelebQueryResponse> celebs = new ArrayList<>();
    private List<RestaurantImageQueryResponse> images = new ArrayList<>();

    public RestaurantSearchResponse(
            Long id, String name,
            String category, String roadAddress,
            Double latitude, Double longitude,
            String phoneNumber, String naverMapUrl,
            Integer viewCount, Double distance,
            int likeCount
    ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.roadAddress = roadAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.naverMapUrl = naverMapUrl;
        this.viewCount = viewCount;
        this.distance = distance.intValue();
        this.likeCount = likeCount;
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
}