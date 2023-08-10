package com.celuveat.common.log.request.logid;

import java.util.UUID;

public class AnonymousLogId extends RequestLogId {

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
