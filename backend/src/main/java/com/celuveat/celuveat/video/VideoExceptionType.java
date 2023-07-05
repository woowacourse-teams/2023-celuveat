package com.celuveat.celuveat.video;

import com.celuveat.celuveat.common.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum VideoExceptionType implements BaseExceptionType {

    NOT_FOUND_VIDEO(300, HttpStatus.NOT_FOUND, "영상을 찾을 수 없습니다"),
    ;

    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    VideoExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
