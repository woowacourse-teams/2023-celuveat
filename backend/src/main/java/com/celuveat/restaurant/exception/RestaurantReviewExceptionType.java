package com.celuveat.restaurant.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.celuveat.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum RestaurantReviewExceptionType implements BaseExceptionType {

    NOT_FOUND_RESTAURANT_REVIEW(NOT_FOUND, "리뷰를 찾을 수 없습니다"),
    PERMISSION_DENIED(FORBIDDEN, "권한이 없습니다"),
    CAN_NOT_LIKE_MY_REVIEW(BAD_REQUEST, "자신의 리뷰에 좋아요 할 수 없습니다"),
    BAD_REVIEW_VALUE(BAD_REQUEST, "줄 수 없는 리뷰 점수입니다."),
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
