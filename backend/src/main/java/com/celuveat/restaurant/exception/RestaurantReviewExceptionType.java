package com.celuveat.restaurant.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.celuveat.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum RestaurantReviewExceptionType implements BaseExceptionType {

    NOT_FOUND_RESTAURANT_REVIEW(NOT_FOUND, "리뷰를 찾을 수 없습니다"),
    PERMISSION_DENIED(UNAUTHORIZED, "권한이 없습니다"),
    RESTAURANT_REVIEW_MISMATCH(BAD_REQUEST, "해당 음식점의 리뷰가 아닙니다"),
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
