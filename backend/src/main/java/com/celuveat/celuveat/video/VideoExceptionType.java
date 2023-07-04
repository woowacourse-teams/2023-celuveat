package com.celuveat.celuveat.video;

import com.celuveat.celuveat.common.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum VideoExceptionType implements BaseExceptionType {

    ;

    @Override
    public int errorCode() {
        return 0;
    }

    @Override
    public HttpStatus httpStatus() {
        return null;
    }

    @Override
    public String errorMessage() {
        return null;
    }
}
