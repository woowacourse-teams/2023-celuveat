package com.celuveat.celuveat.celeb.exception;

import com.celuveat.celuveat.common.exception.BaseException;
import com.celuveat.celuveat.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CelebException extends BaseException {

    private final CelebExceptionType exceptionType;

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}
