package com.celuveat.acceptance.common;

import static com.celuveat.common.auth.AuthConstant.JSESSION_ID;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.common.exception.BaseExceptionType;
import com.celuveat.common.exception.ExceptionResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.http.HttpStatus;

public class AcceptanceSteps {

    public static final HttpStatus 정상_처리 = HttpStatus.OK;
    public static final HttpStatus 생성됨 = HttpStatus.CREATED;
    public static final HttpStatus 잘못된_요청 = HttpStatus.BAD_REQUEST;
    public static final HttpStatus 권한_없음 = HttpStatus.FORBIDDEN;
    public static final HttpStatus 찾을수_없음 = HttpStatus.NOT_FOUND;
    public static final HttpStatus 중복됨 = HttpStatus.CONFLICT;
    public static final HttpStatus 내용_없음 = HttpStatus.NO_CONTENT;

    public static final Object 없음 = null;

    public static LocalDateTime 현재시간() {
        return LocalDateTime.now();
    }

    public static RequestSpecification given() {
        return RestAssured
                .given()
                .contentType(JSON);
    }

    public static RequestSpecification given(String 세션_ID) {
        return RestAssured
                .given()
                .cookie(JSESSION_ID, 세션_ID)
                .contentType(JSON);
    }

    public static ExtractableResponse<Response> 로그인을_요청한다() {
        return given()
                .when().get("/oauth/login/kakao?code=abcd")
                .then()
                .extract();
    }

    public static String 세션_아이디를_가져온다(ExtractableResponse<Response> 응답) {
        return 응답.sessionId();
    }

    public static void 응답_상태를_검증한다(
            ExtractableResponse<Response> 응답,
            HttpStatus 예상_상태
    ) {
        assertThat(응답.statusCode()).isEqualTo(예상_상태.value());
    }

    public static void 발생한_예외를_검증한다(
            ExtractableResponse<Response> 응답,
            BaseExceptionType 예외_타입
    ) {
        ExceptionResponse exceptionResponse = 응답.as(ExceptionResponse.class);
        assertThat(exceptionResponse.message())
                .isEqualTo(예외_타입.errorMessage());
    }

    public static void 잘못된_요청_예외를_검증한다(ExtractableResponse<Response> 잘못된_요청_응답) {
        assertThat(잘못된_요청_응답.statusCode()).isEqualTo(잘못된_요청.value());
    }

    public static <T> void 값이_존재한다(Optional<T> t) {
        assertThat(t).isPresent();
    }

    public static <T> void 값이_존재한다(T t) {
        assertThat(t).isNotNull();
    }

    public static <T> void 두_값이_같다(T 첫번째_값, T 두번째_값) {
        assertThat(첫번째_값).isEqualTo(두번째_값);
    }
}
