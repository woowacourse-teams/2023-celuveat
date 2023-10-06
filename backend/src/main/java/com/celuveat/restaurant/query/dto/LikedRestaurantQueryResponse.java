package com.celuveat.restaurant.query.dto;

import com.celuveat.restaurant.command.domain.Restaurant;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikedRestaurantQueryResponse {

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
    private List<CelebQueryResponse> celebs = new ArrayList<>();
    private List<RestaurantImageQueryResponse> images = new ArrayList<>();

    public static LikedRestaurantQueryResponse from(
            Restaurant restaurant,
            List<CelebQueryResponse> celebs,
            List<RestaurantImageQueryResponse> images
    ) {
        return new LikedRestaurantQueryResponse(
                restaurant.id(),
                restaurant.name(),
                restaurant.category(),
                restaurant.roadAddress(),
                restaurant.latitude(),
                restaurant.longitude(),
                restaurant.phoneNumber(),
                restaurant.naverMapUrl(),
                celebs,
                images
        );
    }
}
