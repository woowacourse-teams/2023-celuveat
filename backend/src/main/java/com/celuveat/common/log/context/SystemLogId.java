package com.celuveat.common.log.context;

import java.util.UUID;

public class SystemLogId implements LogId {

    private final String id;

    private SystemLogId(String id) {
        this.id = id;
    }

    public static SystemLogId randomId() {
        return new SystemLogId(UUID.randomUUID().toString().substring(0, 8));
    }

    @Override
    public String logId() {
        return "System ID = "+id;
    }
}
