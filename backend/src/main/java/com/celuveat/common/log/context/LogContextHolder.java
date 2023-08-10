package com.celuveat.common.log.context;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class LogContextHolder {

    private LogContext logContext;

    public void setLogContext(LogContext logContext) {
        this.logContext = logContext;
    }

    public LogContext get() {
        return logContext;
    }

    public void increaseCall() {
        logContext.increaseCall();
    }

    public void decreaseCall() {
        logContext.decreaseCall();
    }
}
