package com.celuveat.admin.command.application.dto;

public record SaveImageCommand(
        Long restaurantId,
        String author,
        String name,
        String socialMedia
) {
}
