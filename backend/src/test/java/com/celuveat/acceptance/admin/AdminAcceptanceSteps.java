package com.celuveat.acceptance.admin;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static io.restassured.http.ContentType.TEXT;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.celuveat.admin.presentation.dto.SaveCelebRequest;
import com.celuveat.admin.presentation.dto.SaveDataRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;

public class AdminAcceptanceSteps {

    public static final String TAB = "\t";
    public static final String 줄바꿈 = System.lineSeparator();

    public static String 데이터_입력_생성(
            String 셀럽_이름,
            String 음식점_이름,
            String 유튜브_영상_링크,
            String 영상_업로드_일,
            String 인스타_아이디
    ) {
        return 음식점_이름 +
                TAB + "@" + 셀럽_이름 +
                TAB + 유튜브_영상_링크 +
                TAB + 영상_업로드_일 +
                TAB + 음식점_이름 + " 주소" +
                TAB + "전화번호" +
                TAB + "카테고리" +
                TAB + "12.3456" +
                TAB + "12.3456" +
                TAB + "음식점네이버링크" +
                TAB + 음식점_이름 + ".png" +
                TAB + 인스타_아이디;
    }

    public static String 데이터_입력_생성(String 셀럽_이름, String 음식점_이름) {
        return 데이터_입력_생성(셀럽_이름, 음식점_이름, "유튜브영상링크", "2027. 7. 2.", "");
    }

    public static String 데이터_입력_생성(String 셀럽_이름, String 음식점_이름, String 인스타_아이디) {
        return 데이터_입력_생성(셀럽_이름, 음식점_이름, "유튜브영상링크", "2027. 7. 2.", 인스타_아이디);
    }

    public static List<SaveDataRequest> 데이터_저장_요청_생성(String input) {
        String[] rows = input.split(System.lineSeparator());
        return Arrays.stream(rows)
                .map(row -> row.split(TAB, -1))
                .map(SaveDataRequest::from)
                .toList();
    }

    public static ExtractableResponse<Response> 데이터_저장_요청(String data) {
        return given()
                .contentType(TEXT.withCharset(UTF_8))
                .body(data)
                .when().post("/admin/data")
                .then()
                .extract();
    }

    public static String 셀럽_입력_생성(String 셀럽_이름) {
        return 셀럽_이름
                + "\t@%s".formatted(셀럽_이름)
                + "\thttps://profileImageUrl";
    }

    public static List<SaveCelebRequest> 셀럽_저장_요청_생성(String input) {
        String[] rows = input.split(System.lineSeparator());
        return Arrays.stream(rows)
                .map(row -> row.split(TAB, -1))
                .map(SaveCelebRequest::new)
                .toList();
    }

    public static ExtractableResponse<Response> 셀럽_저장_요청(String data) {
        return given()
                .contentType(TEXT.withCharset(UTF_8))
                .body(data)
                .when().post("/admin/celebs")
                .then()
                .extract();
    }
}
