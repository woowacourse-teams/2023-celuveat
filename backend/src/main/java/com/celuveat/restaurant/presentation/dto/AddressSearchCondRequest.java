package com.celuveat.restaurant.presentation.dto;

import com.celuveat.common.exception.BaseException;
import com.celuveat.common.exception.BaseExceptionType;
import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.AddressSearchCond;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;

public record AddressSearchCondRequest(
        String value
) {

    private static final Map<String, List<String>> mapping = new HashMap<>() {
        {
            put("잠실", List.of(
                    "서울 송파구 한가람로", "서울 송파구 석촌호수로", "서울 강남구 양재대로",
                    "서울 강남구 도곡로", "서울 송파구 올림픽로", "서울 송파구 백제고분로",
                    "서울 송파구 삼전로", "서울 송파구 잠실로"));
            put("성수", List.of(
                    "서울 성동구 성수일로", "서울 성동구 성수이로", "서울 광진구 뚝섬로",
                    "서울 광진구 광나루로", "서울 성동구 서울숲길", "서울 강남구 도산대로",
                    "서울 강남구 가로수길", "서울 성동구 연무장길", "서울 광진구 강변북로"));
            put("홍대,합정", List.of(
                    "서울 마포구 와우산로", "서울 마포구 홍익로", "서울 마포구 희우정로",
                    "서울 마포구 어울마당로", "서울 마포구 월드컵로", "서울 마포구 성지3길"));
            put("을지로,종로", List.of(
                    "서울 중구 을지로", "서울 중구 충무로", "서울 종로구 대학로",
                    "서울 동대문구 청계천로", "서울 종로구 세종대로", "서울 종로구 창경궁로"));
            put("압구정,청담", List.of(
                    "서울 강남구 도산대로", "서울 강남구 가로수길", "서울 강남구 압구정로",
                    "서울 강남구 논현로"));
            put("여의도", List.of(
                    "서울 영등포구 여의서로", "서울 영등포구 의사당대로", "서울 영등포구 여의공원로",
                    "서울 동작구 여의대방로", "서울 영등포구 국제금융로", "서울 영등포구 여의동로",
                    "서울 동작구 여의대방로"));
            put("이태원", List.of(
                    "서울 용산구 회나무로", "서울 용산구 보광로", "서울 용산구 녹사평대로",
                    "서울 용산구 이태원로", "서울 용산구 남산공원길"));
            put("속초,강릉,양양", List.of("강원 속초시", "강원 강릉시", "강원 양양군"));
            put("부산", List.of("부산"));
            put("제주", List.of("제주"));
        }
    };

    public AddressSearchCond toCondition() {
        List<String> addresses = mapping.get(value);
        if (addresses == null) {
            throw new BadRequestException(value);
        }
        return new AddressSearchCond(addresses);
    }

    private static class BadRequestException extends BaseException {

        private final BadRequestExceptionType badRequestExceptionType;

        private BadRequestException(String value) {
            this.badRequestExceptionType = new BadRequestExceptionType(value);
        }

        @Override
        public BaseExceptionType exceptionType() {
            return badRequestExceptionType;
        }
    }

    private record BadRequestExceptionType(
            String value
    ) implements BaseExceptionType {

        @Override
        public HttpStatus httpStatus() {
            return HttpStatus.BAD_REQUEST;
        }

        @Override
        public String errorMessage() {
            return value + "에 대응되는 주소들이 없습니다.";
        }
    }

}