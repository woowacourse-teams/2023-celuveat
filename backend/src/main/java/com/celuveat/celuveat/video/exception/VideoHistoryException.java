package com.celuveat.celuveat.video.exception;

import com.celuveat.celuveat.common.exception.BaseException;
import com.celuveat.celuveat.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VideoHistoryException extends BaseException {

    private final VideoHistoryExceptionType exceptionType;

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }
}
