package com.celuveat.common.log.context;

public class LogContext {

    private final LogId logId;
    private final long startTimeMillis;
    private int methodDepth = 0;

    public LogContext(LogId logId) {
        this.logId = logId;
        this.startTimeMillis = System.currentTimeMillis();
    }

    public void increaseCall() {
        methodDepth++;
    }

    public void decreaseCall() {
        methodDepth--;
    }

    public String logId() {
        return logId.logId();
    }

    public String depthPrefix(String prefixString) {
        if (methodDepth == 1) {
            return "|" + prefixString;
        }
        String bar = "|" + " ".repeat(prefixString.length());
        return bar.repeat(methodDepth - 1) + "|" + prefixString;
    }

    public long totalTakenTime() {
        return System.currentTimeMillis() - startTimeMillis;
    }
}
