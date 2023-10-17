package com.celuveat.event.command.application;

public class EventImageNameGenerator {

    public static String generate(
            String instagramId,
            String restaurantName,
            int number
    ) {
        return instagramId + "_" + restaurantName + "_" + number;
    }
}
