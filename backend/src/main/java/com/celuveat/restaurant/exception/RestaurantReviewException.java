package com.celuveat.restaurant.exception;

import com.celuveat.common.exception.BaseException;
import com.celuveat.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantReviewException extends BaseException {

    private final RestaurantReviewExceptionType exceptionType;

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}
