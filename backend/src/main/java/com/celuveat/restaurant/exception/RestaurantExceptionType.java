package com.celuveat.restaurant.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.celuveat.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum RestaurantExceptionType implements BaseExceptionType {

    NOT_FOUND_RESTAURANT(NOT_FOUND, "음식점을 찾을 수 없습니다"),
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
