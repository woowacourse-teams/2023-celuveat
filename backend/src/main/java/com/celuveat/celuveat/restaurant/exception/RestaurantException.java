package com.celuveat.celuveat.restaurant.exception;

import com.celuveat.celuveat.common.exception.BaseException;
import com.celuveat.celuveat.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantException extends BaseException {

    private final RestaurantExceptionType exceptionType;

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}
