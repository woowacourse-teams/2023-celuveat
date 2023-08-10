package com.celuveat.common.log;

import com.celuveat.common.log.context.LogContext;
import com.celuveat.common.log.context.LogContextHolder;
import com.celuveat.common.log.context.RequestLogId;
import com.celuveat.common.log.context.RequestLogIdHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestLogInterceptor implements HandlerInterceptor {

    private final LogContextHolder logContextHolder;
    private final RequestLogIdHolder requestLogIdHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        RequestLogId logId = RequestLogId.fromRequest(request);
        LogContext logContext = new LogContext(logId);
        requestLogIdHolder.setLogId(logId);
        logContextHolder.setLogContext(logContext);

        Map<String, Object> params = new LinkedHashMap<>();
        params.put("ID", logContext.logId());
        params.put("IP", request.getRemoteAddr());
        params.put("Remote Host", request.getRemoteHost());
        params.put("HOST Header", request.getHeader(HttpHeaders.HOST));
        params.put("ORIGIN", request.getHeader(HttpHeaders.ORIGIN));
        params.put("URL", request.getRequestURL());
        params.put("QueryString", request.getQueryString());
        log.info("[Web Request START] : {}", params);
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
