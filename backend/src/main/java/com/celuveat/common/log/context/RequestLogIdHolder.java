package com.celuveat.common.log.context;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@RequiredArgsConstructor
public class RequestLogIdHolder {

    private RequestLogId logId;

    public void setLogId(RequestLogId logId) {
        this.logId = logId;
    }

    public RequestLogId get() {
        return logId;
    }
}
