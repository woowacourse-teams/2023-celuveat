package com.celuveat.admin.presentation.dto;

import static com.celuveat.admin.exception.AdminExceptionType.ILLEGAL_FORMAT;

import com.celuveat.admin.exception.AdminException;
import com.celuveat.celeb.domain.Celeb;

public record SaveCelebRequest(
        String name,
        String youtubeChannelName,
        String profileImageUrl
) {

    private static final int CELEB_NAME = 0;
    private static final int YOUTUBE_CHANNEL_NAME = 1;
    private static final int PROFILE_IMAGE_URL = 2;

    public SaveCelebRequest(String[] data) {
        this(data[CELEB_NAME], data[YOUTUBE_CHANNEL_NAME], data[PROFILE_IMAGE_URL]);
        validateNullOrBlank(data);
        validateYoutubeChannelNamePattern(data[YOUTUBE_CHANNEL_NAME]);
        validateUrlPattern(data[PROFILE_IMAGE_URL]);
    }

    private void validateNullOrBlank(String[] data) {
        for (String field : data) {
            if (field == null || field.isBlank()) {
                throw new AdminException(ILLEGAL_FORMAT);
            }
        }
    }

    private void validateYoutubeChannelNamePattern(String youtubeChannelName) {
        if (!youtubeChannelName.startsWith("@")) {
            throw new AdminException(ILLEGAL_FORMAT);
        }
    }

    private void validateUrlPattern(String profileImageUrl) {
        if (!profileImageUrl.startsWith("https://")) {
            throw new AdminException(ILLEGAL_FORMAT);
        }
    }

    public Celeb toCeleb() {
        return Celeb.builder()
                .name(name)
                .youtubeChannelName(youtubeChannelName)
                .profileImageUrl(profileImageUrl)
                .build();
    }
}
