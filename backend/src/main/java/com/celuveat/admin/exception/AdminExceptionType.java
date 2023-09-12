package com.celuveat.admin.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.celuveat.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AdminExceptionType implements BaseExceptionType {

    NOT_EXISTS_CELEB(BAD_REQUEST, "셀럽이 저장되어 있지 않습니다. 셀럽 먼저 저장해주세요."),
    ILLEGAL_DATE_FORMAT(BAD_REQUEST, "영상 업로드 날짜 형식이 잘못됐습니다. 'yyyy. M. d' 형식으로 입력해주세요."),
    ILLEGAL_FORMAT(BAD_REQUEST, "데이터 저장 양식이 잘못됐습니다. 엑셀 폼을 준수해주세요."),
    EXIST_NULL(BAD_REQUEST, "입력되지 않은 값이 있습니다."),
    INVALID_YOUTUBE_CHANNEL_NAME(BAD_REQUEST, "유튜브 채널 명이 잘못됐습니다. 앞에 '@'를 붙여주세요."),
    INVALID_URL_PATTERN(BAD_REQUEST, "URL 패턴이 잘못됐습니다. 확인 후 다시 입력해주세요."),
    MISMATCH_COUNT_YOUTUBE_VIDEO_LINK_AND_UPLOAD_DATE(BAD_REQUEST, "유튜브 영상 링크와 업로드 일에 입력된 값의 개수가 다릅니다."),
    MISMATCH_COUNT_IMAGE_NAME_AND_INSTAGRAM_NAME(BAD_REQUEST, "사진 이름의 수와 출처가 1:1 매핑이 아닙니다."),
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
