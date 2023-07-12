package com.celuveat.celuveat.admin.exception;

import com.celuveat.celuveat.common.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum AdminExceptionType implements BaseExceptionType {

    NOT_FOUND_ADMIN(400, HttpStatus.NOT_FOUND, "관리자를 찾을 수 없습니다"),
    NOT_MATCH_PASSWORD(401, HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다"),
    ;

    private final int errorCode;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    AdminExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int errorCode() {
        return errorCode;
    }

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    @Override
    public String errorMessage() {
        return errorMessage;
    }
}
