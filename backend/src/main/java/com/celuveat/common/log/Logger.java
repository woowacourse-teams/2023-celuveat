package com.celuveat.common.log;

import com.celuveat.common.log.context.LogContext;
import com.celuveat.common.log.context.LogContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Logger {

    private static final String CALL_PREFIX = "--->";
    private static final String RETURN_PREFIX = "<---";
    private static final String EX_PREFIX = "<X--";

    private final LogContextHolder logContextHolder;

    public void methodCall(String className, String methodName) {
        LogContext logContext = logContextHolder.get();
        logContextHolder.increaseCall();
        log.info("[{}]  {}",
                formattedLogInfo(logContext.logId()),
                formattedClassAndMethod(logContext.depthPrefix(CALL_PREFIX), className, methodName)
        );
    }

    public void methodReturn(String className, String methodName) {
        LogContext logContext = logContextHolder.get();
        log.info("[{}]  {}   time={}ms  ",
                formattedLogInfo(logContext.logId()),
                formattedClassAndMethod(logContext.depthPrefix(RETURN_PREFIX), className, methodName),
                logContext.totalTakenTime()
        );
        logContextHolder.decreaseCall();
    }

    public void throwException(String className, String methodName, Throwable exception) {
        LogContext logContext = logContextHolder.get();
        log.warn("[{}]  {}   time={}ms,  throws {}  ",
                formattedLogInfo(logContext.logId()),
                formattedClassAndMethod(logContext.depthPrefix(EX_PREFIX), className, methodName),
                logContext.totalTakenTime(),
                exception.getClass().getSimpleName()
        );
        logContextHolder.decreaseCall();
    }

    private String formattedLogInfo(String prefix) {
        return "%19s".formatted(prefix);
    }

    private String formattedClassAndMethod(String prefix, String className, String methodName) {
        return String.format("%-80s", prefix + className + "." + methodName + "()");
    }
}
