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

    private static final String CELEB_SAVE_REQUEST_URL = "/admin/celebs";
    private static final String DATA_SAVE_REQUEST_URL = "/admin/data";
    private static final String TAB = "\t";
    public static final String 줄바꿈 = System.lineSeparator();

    public static String 회사랑의_하늘초밥_데이터_입력_생성() {
        return "하늘초밥" +
                "\thttps://www.youtube.com/watch?v=GeZRxKUCFtM" +
                "\t@RawFishEater" +
                "\t2021. 9. 21." +
                "\thttps://map.naver.com/v5/entry/place/1960457705?entry=plt&c=15,0,0,0,dh" +
                "\t초밥,롤" +
                "\t0507-1366-4573" +
                "\t서울 서대문구 이화여대2길 14 1층 하늘초밥" +
                "\t37.5572713" +
                "\t126.9466788" +
                "\tRawFishEater_하늘초밥_1.jpeg" +
                "\t@RawFishEater";
    }

    public static String 회사랑의_국민연금_데이터_입력_생성() {
        return "국민연금" +
                "\thttps://www.youtube.com/watch?v=rawfisheaterrnralsdusrma" +
                "\t@RawFishEater" +
                "\t2023. 10. 11." +
                "\thttps://map.naver.com/v5/entry/rnralsdursma" +
                "\t한식" +
                "\t010-0234-1234" +
                "\t서울 잠실 송파구청 어딘가" +
                "\t37.234234" +
                "\t214.214123" +
                "\tRawFishEater_국민연금_1.jpeg" +
                "\t@RawFishEater";
    }

    public static String 쯔양의_하늘초밥_데이터_입력_생성() {
        return "하늘초밥" +
                "\thttps://www.youtube.com/watch?v=tzuyangGeZRxKUCFtM" +
                "\t@tzuyang6145" +
                "\t2023. 9. 18." +
                "\thttps://map.naver.com/v5/entry/place/1960457705?entry=plt&c=15,0,0,0,dh" +
                "\t초밥,롤" +
                "\t0507-1366-4573" +
                "\t서울 서대문구 이화여대2길 14 1층 하늘초밥" +
                "\t37.5572713" +
                "\t126.9466788" +
                "\ttzuyang6145_하늘초밥_1.jpeg" +
                "\t@tzuyang6145";
    }

    public static String 성시경의_하늘초밥_데이터_입력_생성() {
        return "하늘초밥" +
                "\thttps://www.youtube.com/watch?v=sungsikyungGeZRxKUCFtM" +
                "\t@sungsikyung" +
                "\t2023. 11. 10." +
                "\thttps://map.naver.com/v5/entry/place/1960457705?entry=plt&c=15,0,0,0,dh" +
                "\t초밥,롤" +
                "\t0507-1366-4573" +
                "\t서울 서대문구 이화여대2길 14 1층 하늘초밥" +
                "\t37.5572713" +
                "\t126.9466788" +
                "\tsungsikyung_하늘초밥_1.jpeg" +
                "\t@sungsikyung";
    }

    public static String 데이터_입력_생성(String 셀럽_이름, String 음식점_이름) {
        return 데이터_입력_생성(셀럽_이름, 음식점_이름, "유튜브영상링크", "2027. 7. 2.", "");
    }

    public static String 데이터_입력_생성(String 셀럽_이름, String 음식점_이름, String 인스타_아이디) {
        return 데이터_입력_생성(셀럽_이름, 음식점_이름, "유튜브영상링크", "2027. 7. 2.", 인스타_아이디);
    }

    public static String 영상_업로드_날짜가_잘못된_입력_생성(String 셀럽_이름, String 음식점_이름, String 영상_업로드_날짜) {
        return 데이터_입력_생성(셀럽_이름, 음식점_이름, "유튜브영상링크", 영상_업로드_날짜, "");
    }

    public static String 데이터_입력_생성(
            String 셀럽_이름,
            String 음식점_이름,
            String 유튜브_영상_링크,
            String 영상_업로드_일,
            String 인스타_아이디
    ) {
        return 음식점_이름 +
                TAB + 유튜브_영상_링크 +
                TAB + "@" + 셀럽_이름 +
                TAB + 영상_업로드_일 +
                TAB + "음식점네이버링크" +
                TAB + "카테고리" +
                TAB + "전화번호" +
                TAB + 음식점_이름 + " 주소" +
                TAB + "12.3456" +
                TAB + "12.3456" +
                TAB + 음식점_이름 +
                TAB + 인스타_아이디;
    }

    public static List<SaveDataRequest> 데이터저장_요청_객체_생성(String rawData) {
        String[] rows = rawData.split(System.lineSeparator());
        return Arrays.stream(rows)
                .map(row -> row.split(TAB, -1))
                .map(SaveDataRequest::from)
                .toList();
    }

    public static ExtractableResponse<Response> 데이터_저장_요청(String data) {
        return given()
                .contentType(TEXT.withCharset(UTF_8))
                .body(data)
                .when().post(DATA_SAVE_REQUEST_URL)
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
                .when().post(CELEB_SAVE_REQUEST_URL)
                .then()
                .extract();
    }
}
