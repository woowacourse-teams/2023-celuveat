package com.celuveat.celuveat.celeb.exception;

import com.celuveat.celuveat.common.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum CelebExceptionType implements BaseExceptionType {

    NOT_FOUND_CELEB(100, HttpStatus.NOT_FOUND, "셀럽을 찾을 수 없습니다"),
    ;

    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    CelebExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int errorCode() {
        return errorCode;
    }

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    @Override
    public String errorMessage() {
        return errorMessage;
    }
}
