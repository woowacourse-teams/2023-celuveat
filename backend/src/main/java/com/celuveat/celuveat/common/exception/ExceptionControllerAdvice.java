package com.celuveat.celuveat.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    private static final String UNEXPECTED_ERROR_CODE = "1";

    @ExceptionHandler(BaseException.class)
    ResponseEntity<ExceptionResponse> handleException(BaseException e) {
        BaseExceptionType type = e.exceptionType();
        log.warn("[WARN] 예외 내용: {}", type.errorMessage());
        ExceptionResponse response =
                new ExceptionResponse(String.valueOf(type.errorCode()), type.errorMessage());
        return new ResponseEntity<>(response, type.httpStatus());
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ExceptionResponse> handleException(Exception e) {
        log.error("[ERROR] 원인을 알 수 없는 예외", e);
        ExceptionResponse response =
                new ExceptionResponse(UNEXPECTED_ERROR_CODE, "알 수 없는 오류가 발생했습니다.");
        return ResponseEntity.internalServerError()
                .body(response);
    }
}
