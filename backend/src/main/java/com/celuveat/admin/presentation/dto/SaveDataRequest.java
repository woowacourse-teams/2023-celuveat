package com.celuveat.admin.presentation.dto;

import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImage;
import com.celuveat.restaurant.command.domain.SocialMedia;
import com.celuveat.video.command.domain.Video;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record SaveDataRequest(
        String youtubeChannelName,
        String imageName,
        String youtubeVideoUrl,
        String restaurantName,
        String roadAddress,
        String phoneNumber,
        String category,
        String naverMapUrl,
        String videoUploadDate,
        String latitude,
        String longitude,
        String instagramName,
        String superCategory
) {

    private static final int RESTAURANT_NAME = 0;
    private static final int YOUTUBE_VIDEO_URL = 1;
    private static final int YOUTUBE_CHANNEL_NAME = 2;
    private static final int UPLOAD_DATE = 3;
    private static final int NAVER_MAP_URL = 4;
    private static final int CATEGORY = 5;
    private static final int PHONE_NUMBER = 6;
    private static final int ROAD_ADDRESS = 7;
    private static final int LATITUDE = 8;
    private static final int LONGITUDE = 9;
    private static final int IMAGE_NAME = 10;
    private static final int INSTAGRAM_NAME = 11;
    private static final int SUPER_CATEGORY = 12;

    public static SaveDataRequest from(String[] data) {
        return SaveDataRequest.builder()
                .youtubeChannelName(data[YOUTUBE_CHANNEL_NAME])
                .imageName(data[IMAGE_NAME])
                .youtubeVideoUrl(data[YOUTUBE_VIDEO_URL])
                .videoUploadDate(data[UPLOAD_DATE])
                .restaurantName(data[RESTAURANT_NAME])
                .roadAddress(data[ROAD_ADDRESS])
                .phoneNumber(data[PHONE_NUMBER])
                .category(data[CATEGORY])
                .naverMapUrl(data[NAVER_MAP_URL])
                .latitude(data[LATITUDE])
                .longitude(data[LONGITUDE])
                .instagramName(data[INSTAGRAM_NAME])
                .superCategory(data[SUPER_CATEGORY])
                .build();
    }

    public Restaurant toRestaurant() {
        return Restaurant.builder()
                .name(restaurantName)
                .category(category)
                .roadAddress(roadAddress)
                .latitude(Double.parseDouble(latitude))
                .longitude(Double.parseDouble(longitude))
                .phoneNumber(phoneNumber)
                .naverMapUrl(naverMapUrl)
                .build();
    }

    public RestaurantImage toRestaurantImage(
            String imageName,
            String instagramName,
            Restaurant restaurant
    ) {
        SocialMedia socialMedia = SocialMedia.from(instagramName);
        String author = switch (socialMedia) {
            case INSTAGRAM -> instagramName;
            default -> youtubeChannelName;
        };

        return RestaurantImage.builder()
                .name(imageName)
                .author(author)
                .socialMedia(socialMedia)
                .restaurant(restaurant)
                .build();
    }

    public Video toVideo(String youtubeVideoUrl, LocalDate uploadDate, Celeb celeb, Restaurant restaurant) {
        return Video.builder()
                .youtubeUrl(youtubeVideoUrl)
                .uploadDate(uploadDate)
                .celeb(celeb)
                .restaurant(restaurant)
                .build();
    }
}
