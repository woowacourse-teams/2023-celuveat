package com.celuveat.admin.presentation.dto;

import com.celuveat.admin.command.application.dto.SaveImageCommand;
import org.springframework.web.multipart.MultipartFile;

public record SaveImageRequest(
        Long restaurantId,
        String author,
        String name,
        String socialMedia,
        MultipartFile image
) {

    public SaveImageCommand toCommand() {
        return new SaveImageCommand(restaurantId, author, name, socialMedia);
    }
}
