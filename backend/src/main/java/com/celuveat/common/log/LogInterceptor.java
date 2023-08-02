package com.celuveat.common.log;

import com.celuveat.common.log.context.LogContext;
import com.celuveat.common.log.context.LogContextHolder;
import com.celuveat.common.log.context.RequestLogId;
import com.celuveat.common.log.context.RequestLogIdHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogInterceptor implements HandlerInterceptor {

    private final LogContextHolder logContextHolder;
    private final RequestLogIdHolder requestLogIdHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        RequestLogId logId = RequestLogId.fromRequest(request);
        LogContext logContext = new LogContext(logId);
        requestLogIdHolder.setLogId(logId);
        logContextHolder.setLogContext(logContext);
        log.info("[Web Request Start]  ID: {}, IP: {}, HOST: {}, ORIGIN: {}, URL: {}",
                logContext.logId(),
                request.getRemoteAddr(),
                request.getRemoteHost(),
                request.getHeader("Origin"),
                request.getRequestURL()
        );
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) {
        RequestLogId requestLogId = requestLogIdHolder.get();
        log.info("[Web Request END]  ID: {}", requestLogId.logId());
    }
}
