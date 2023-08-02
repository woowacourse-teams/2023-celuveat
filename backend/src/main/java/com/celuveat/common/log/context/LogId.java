package com.celuveat.common.log.context;

public interface LogId {

    static LogId defaultId() {
        return SystemLogId.randomId();
    }

    String logId();
}
