package com.celuveat.celuveat.video.exception;

import com.celuveat.celuveat.common.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum VideoHistoryExceptionType implements BaseExceptionType {

    NOT_FOUND_VIDEO_HISTORY(500, HttpStatus.NOT_FOUND, "영상이력을 찾을 수 없습니다"),
    ;

    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    VideoHistoryExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
