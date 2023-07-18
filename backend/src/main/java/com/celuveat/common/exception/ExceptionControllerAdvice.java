package com.celuveat.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(BaseException.class)
    ResponseEntity<ExceptionResponse> handleException(BaseException e) {
        BaseExceptionType type = e.exceptionType();
        log.warn("[WARN] 잘못된 요청이 들어왔습니다. 내용 -> {}", type.errorMessage());
        return new ResponseEntity<>(
                new ExceptionResponse(type.errorMessage()),
                type.httpStatus());
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ExceptionResponse> handleException(Exception e) {
        log.error("[ERROR] 예상하지 못한 예외가 발생했습니다.", e);
        return ResponseEntity.internalServerError()
                .body(new ExceptionResponse("알 수 없는 오류가 발생했습니다."));
    }
}
