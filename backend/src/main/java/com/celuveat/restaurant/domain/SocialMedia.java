package com.celuveat.restaurant.domain;

public enum SocialMedia {

    YOUTUBE,
    INSTAGRAM,
    ;

    public static SocialMedia getBasedOn(String instagramName) {
        if (instagramName.strip().isBlank()) {
            return YOUTUBE;
        }
        return INSTAGRAM;
    }
}
