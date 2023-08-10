package com.celuveat.common.log.context;

import org.springframework.stereotype.Component;

@Component
public class LogContextHolder {

    private final ThreadLocal<LogContext> holder = new ThreadLocal<>();

    public void setLogContext(LogContext logContext) {
        holder.set(logContext);
    }

    public LogContext getOrCreate() {
        LogContext logContext = holder.get();
        if (logContext == null) {
            logContext = new LogContext(LogId.defaultId());
            setLogContext(logContext);
        }
        return logContext;
    }

    public void increaseCall() {
        LogContext logContext = holder.get();
        logContext.increaseCall();
    }

    public void decreaseCall() {
        LogContext logContext = getOrCreate();
        logContext.decreaseCall();
        if (logContext.isFinal()) {
            holder.remove();
        }
    }
}
