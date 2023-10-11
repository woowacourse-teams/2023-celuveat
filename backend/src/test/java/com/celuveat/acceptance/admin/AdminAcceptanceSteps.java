package com.celuveat.acceptance.admin;

import static com.celuveat.acceptance.common.AcceptanceSteps.given;
import static com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps.멀티파트_스팩을_추출한다;
import static io.restassured.http.ContentType.TEXT;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.celuveat.acceptance.restaurant.RestaurantAcceptanceSteps;
import com.celuveat.admin.presentation.dto.SaveCelebRequest;
import com.celuveat.admin.presentation.dto.SaveDataRequest;
import com.celuveat.admin.presentation.dto.SaveImageRequest;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class AdminAcceptanceSteps {

    public static final String 줄바꿈 = System.lineSeparator();
    private static final String CELEB_SAVE_REQUEST_URL = "/admin/celebs";
    private static final String DATA_SAVE_REQUEST_URL = "/admin/data";
    private static final String TAB = "\t";

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
                TAB + 인스타_아이디 +
                TAB + "슈퍼 카테고리";
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

    public static SaveImageRequest 음식점_이미지_저장_요청_데이터(Long 음식점_아이디, String 저자, String 소셜미디어, String 사진이름) {
        MultipartFile image = RestaurantAcceptanceSteps.multipartFile(사진이름);
        return new SaveImageRequest(음식점_아이디, 저자, 사진이름, 소셜미디어, image);
    }

    public static ExtractableResponse<Response> 음식점_이미지_저장_요청(SaveImageRequest 요청) {
        var restaurantId = new MultiPartSpecBuilder(요청.restaurantId())
                .controlName("restaurantId")
                .charset(UTF_8)
                .build();
        var author = new MultiPartSpecBuilder(요청.author())
                .controlName("author")
                .charset(UTF_8)
                .build();
        var name = new MultiPartSpecBuilder(요청.name())
                .controlName("name")
                .charset(UTF_8)
                .build();
        var socialMedia = new MultiPartSpecBuilder(요청.socialMedia())
                .controlName("socialMedia")
                .charset(UTF_8)
                .build();
        RequestSpecification requestSpecification = given()
                .multiPart(restaurantId)
                .multiPart(author)
                .multiPart(name)
                .multiPart(socialMedia);
        requestSpecification.multiPart(멀티파트_스팩을_추출한다("image", 요청.image()));
        return requestSpecification
                .contentType("multipart/form-data")
                .when().post("/admin/images")
                .then().log().all()
                .extract();
    }
}
