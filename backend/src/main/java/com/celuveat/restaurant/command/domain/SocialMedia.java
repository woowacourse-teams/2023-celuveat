package com.celuveat.restaurant.command.domain;

public enum SocialMedia {

    YOUTUBE,
    INSTAGRAM,
    ;

    public static SocialMedia from(String instagramName) {
        if (instagramName.strip().isBlank()) {
            return YOUTUBE;
        }
        return INSTAGRAM;
    }
}
