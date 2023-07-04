package com.celuveat.celuveat.restaurant;

import com.celuveat.celuveat.common.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum RestaurantExceptionType implements BaseExceptionType {

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
