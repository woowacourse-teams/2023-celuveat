package com.celuveat.restaurant.command.domain;

import static com.celuveat.restaurant.exception.RestaurantImageExceptionType.UNSUPPORTED_SOCIAL_MEDIA;

import com.celuveat.restaurant.exception.RestaurantImageException;
import java.util.Arrays;

public enum SocialMedia {

    YOUTUBE("youtube"),
    INSTAGRAM("instagram"),
    ;

    private final String value;

    SocialMedia(String value) {
        this.value = value;
    }

    public static SocialMedia from(String value) {
        String socialMedia = value.strip().toLowerCase();
        return Arrays.stream(values())
                .filter(it -> it.value.equals(socialMedia))
                .findAny()
                .orElseThrow(() -> new RestaurantImageException(UNSUPPORTED_SOCIAL_MEDIA));
    }
}
