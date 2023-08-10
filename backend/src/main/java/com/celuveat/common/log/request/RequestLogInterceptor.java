package com.celuveat.common.log.request;

import com.celuveat.common.log.context.LogContext;
import com.celuveat.common.log.context.LogContextHolder;
import com.celuveat.common.log.context.LogId;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestLogInterceptor implements HandlerInterceptor {

    private final LogContextHolder logContextHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        LogId logId = LogId.fromRequest(request);
        LogContext logContext = new LogContext(logId);
        logContextHolder.setLogContext(logContext);
        log.info("[Web Request START] : [\n{}]", new RequestInfoLogData(logContext.logId(), request));
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) {
        LogContext logContext = logContextHolder.get();
        log.info("[Web Request END] : [\n{}]", new ResponseInfoLogData(logContext.logId(), response));
    }
}
