package com.celuveat.celuveat.restaurantlinkfetcher.infra.youtube.api;

import java.util.Map;

public enum Channel {

    TZUYANG(
            "UCfpaSruWW3S4dibonKXENjA",
            "youtube/search/tzuyang/tzuyang%s.json",
            Map.ofEntries(
                    Map.entry("01", "CDIQAQ"),
                    Map.entry("02", "CDIQAA"),
                    Map.entry("03", "CGQQAA"),
                    Map.entry("04", "CJYBEAA"),
                    Map.entry("05", "CMgBEAA"),
                    Map.entry("06", "CPoBEAA"),
                    Map.entry("07", "CKwCEAA"),
                    Map.entry("08", "CN4CEAA"),
                    Map.entry("09", "CJADEAA")
            )
    ),
    HEEBAB(
            "UCA6KBBX8cLwYZNepxlE_7SA",
            "youtube/search/heebab/heebab%s.json",
            Map.ofEntries(
                    Map.entry("01", "CDIQAQ"),
                    Map.entry("02", "CDIQAA"),
                    Map.entry("03", "CGQQAA"),
                    Map.entry("04", "CJYBEAA"),
                    Map.entry("05", "CMgBEAA"),
                    Map.entry("06", "CPoBEAA"),
                    Map.entry("07", "CKwCEAA"),
                    Map.entry("08", "CN4CEAA"),
                    Map.entry("09", "CJADEAA"),
                    Map.entry("10", "CMIDEAA"),
                    Map.entry("11", "CPQDEAA")
            )
    ),
    ;

    private final String channelId;
    private final String filepath;
    private final Map<String, String> tokenByFilename;

    Channel(String channelId, String filepath, Map<String, String> tokenByFilename) {
        this.channelId = channelId;
        this.filepath = filepath;
        this.tokenByFilename = tokenByFilename;
    }

    public String channelId() {
        return channelId;
    }

    public String filepath() {
        return filepath;
    }

    public Map<String, String> tokenByFilename() {
        return tokenByFilename;
    }
}
