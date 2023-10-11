package com.celuveat.event.presentation.dto;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record SubmitEventImageRequest(
        String instagramId,
        String restaurantName,
        List<MultipartFile> images
) {
    public SubmitEventImageRequest {
        instagramId = instagramId.strip();
        restaurantName = restaurantName.strip();
    }
}
