package com.celuveat.restaurant.exception;

import com.celuveat.common.exception.BaseException;
import com.celuveat.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantImageSuggestionException extends BaseException {

    private final RestaurantImageSuggestionExceptionType exceptionType;

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}
