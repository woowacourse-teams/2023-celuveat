package com.celuveat.common.log.request;

import com.celuveat.common.log.context.LogContext;
import com.celuveat.common.log.context.LogContextHolder;
import com.celuveat.common.log.context.LogId;
import com.celuveat.common.log.query.QueryCounter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestLogInterceptor implements HandlerInterceptor {

    private static final int QUERY_COUNT_WARNING_STANDARD = 5;
    private static final int TOTAL_TIME_WARNING_STANDARD_MS = 2500;

    private final QueryCounter queryCounter;
    private final LogContextHolder logContextHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        LogId logId = LogId.fromRequest(request);
        LogContext logContext = new LogContext(logId);
        logContextHolder.setLogContext(logContext);
        if (isPreflight(request)) {
            log.info("[Preflight Request] : [\n{}]", logId);
            return true;
        }
        RequestInfoLogData requestInfoLogData = new RequestInfoLogData(logContext.logId(), request);
        requestInfoLogData.put("Controller Method", handlerMethod((HandlerMethod) handler));
        log.info("[Web Request START] : [\n{}]", requestInfoLogData);
        return true;
    }

    private boolean isPreflight(HttpServletRequest request) {
        return request.getMethod().equals(HttpMethod.OPTIONS.name());
    }

    private String handlerMethod(HandlerMethod handler) {
        String className = handler.getMethod().getDeclaringClass().getSimpleName();
        String methodName = handler.getMethod().getName();
        return className + "." + methodName + "()";
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) {
        LogContext logContext = logContextHolder.get();
        if (isPreflight(request)) {
            log.info("[Preflight Request] : [\n{}]", logContext.logId());
            return;
        }
        ResponseInfoLogData responseInfoLogData = new ResponseInfoLogData(logContext.logId(), response);
        responseInfoLogData.put("Query Count", queryCounter.count());
        long totalTime = logContext.totalTakenTime();
        responseInfoLogData.put("Total Time", totalTime + "ms");
        log.info("[Web Request END] : [\n{}]", responseInfoLogData);
        if (queryCounter.count() >= QUERY_COUNT_WARNING_STANDARD) {
            log.warn("[{}] : 쿼리가 {}번 이상 실행되었습니다. (총 {}번)",
                    logContext.logId(),
                    QUERY_COUNT_WARNING_STANDARD,
                    queryCounter.count()
            );
        }
        if (totalTime >= TOTAL_TIME_WARNING_STANDARD_MS) {
            log.warn("[{}] : 요청을 처리하는데 {}ms 이상 소요되었습니다. (총 {}ms)",
                    logContext.logId(),
                    TOTAL_TIME_WARNING_STANDARD_MS,
                    totalTime
            );
        }
    }
}
