package com.celuveat.common.log.context;

import java.util.UUID;

public class AuthenticatedLogId implements LogId {

    private final String memberId;
    private final String requestId;

    public AuthenticatedLogId(Object memberId) {
        this.memberId = String.valueOf(memberId);
        this.requestId = UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    public String logId() {
        return memberId + "(memberId)" + "-" + requestId;
    }
}
