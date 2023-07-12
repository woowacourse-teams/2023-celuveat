package com.celuveat.celuveat.restaurantvideohistoryfetcher.exception;

import com.celuveat.celuveat.common.exception.BaseException;
import com.celuveat.celuveat.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantVideoHistoryFetcherException extends BaseException {

    private final RestaurantVideoHistoryFetcherExceptionType exceptionType;

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}
