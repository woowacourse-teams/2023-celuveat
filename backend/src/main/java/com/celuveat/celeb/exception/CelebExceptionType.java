package com.celuveat.celeb.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.celuveat.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CelebExceptionType implements BaseExceptionType {

    NOT_FOUND_CELEB(BAD_REQUEST, "셀럽이 존재하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorMessage;

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    @Override
    public String errorMessage() {
        return errorMessage;
    }
}
