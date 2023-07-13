package com.celuveat.celuveat.celeb.infra.youtube.api;

public enum Channel {

    TZUYANG("UCfpaSruWW3S4dibonKXENjA", "tzuyang"),
    HEEBAB("UCA6KBBX8cLwYZNepxlE_7SA", "heebab"),
    ;

    private final String channelId;
    private final String filename;

    Channel(String channelId, String filename) {
        this.channelId = channelId;
        this.filename = filename;
    }

    public String channelId() {
        return channelId;
    }

    public String filename() {
        return filename;
    }
}
