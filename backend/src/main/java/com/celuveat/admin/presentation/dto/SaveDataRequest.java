package com.celuveat.admin.presentation.dto;

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
        LocalDate videoUploadDate,
        String latitude,
        String longitude
) {

    private static final int YOUTUBE_CHANNEL_NAME = 0;
    private static final int IMAGE_NAME = 1;
    private static final int YOUTUBE_VIDEO_URL = 2;
    private static final int UPLOAD_DATE = 3;
    private static final int RESTAURANT_NAME = 4;
    private static final int ROAD_ADDRESS = 5;
    private static final int PHONE_NUMBER = 6;
    private static final int CATEGORY = 7;
    private static final int NAVER_MAP_URL = 8;
    private static final int LATITUDE = 9;
    private static final int LONGITUDE = 10;


    public static SaveDataRequest from(String[] data) {
        return SaveDataRequest.builder()
                .youtubeChannelName(data[YOUTUBE_CHANNEL_NAME])
                .imageName(data[IMAGE_NAME])
                .youtubeVideoUrl(data[YOUTUBE_VIDEO_URL])
                .videoUploadDate(LocalDate.parse(data[UPLOAD_DATE]))
                .restaurantName(data[RESTAURANT_NAME])
                .roadAddress(data[ROAD_ADDRESS])
                .phoneNumber(data[PHONE_NUMBER])
                .category(data[CATEGORY])
                .naverMapUrl(data[NAVER_MAP_URL])
                .latitude(data[LATITUDE])
                .longitude(data[LONGITUDE])
                .build();
    }
}
