package com.celuveat.common.log.context;

import java.util.UUID;

public class AnonymousLogId implements LogId {

    private final String id;

    private AnonymousLogId(String id) {
        this.id = id;
    }

    public static AnonymousLogId randomId() {
        return new AnonymousLogId(UUID.randomUUID().toString().substring(0, 8));
    }

    @Override
    public String logId() {
        return id + "(anonymous)";
    }
}
