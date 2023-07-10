package com.celuveat.celuveat.acceptance.video;

import static com.celuveat.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.히밥;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.교촌치킨;
import static com.celuveat.celuveat.restaurant.fixture.RestaurantFixture.맥도날드;
import static com.celuveat.celuveat.video.fixture.VideoFixture.toFindAllVideoByRestaurantIdResponse;
import static com.celuveat.celuveat.video.fixture.VideoFixture.영상;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.celeb.infra.persistence.CelebDao;
import com.celuveat.celuveat.restaurant.domain.Restaurant;
import com.celuveat.celuveat.restaurant.infra.persistence.RestaurantDao;
import com.celuveat.celuveat.video.application.dto.FindAllVideoByRestaurantIdResponse;
import com.celuveat.celuveat.video.domain.Video;
import com.celuveat.celuveat.video.infra.persistence.VideoDao;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@Sql("/truncate.sql")
@DisplayName("Video 인수테스트")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class VideoAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CelebDao celebDao;

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private VideoDao videoDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 음식점_ID로_영상을_조회한다() {
        // given
        Celeb 히밥 = 히밥();
        Celeb 성시경 = 성시경();
        Restaurant 맥도날드 = 맥도날드();
        Restaurant 교촌치킨 = 교촌치킨();
        Long 히밥_ID = celebDao.save(히밥);
        Long 성시경_ID = celebDao.save(성시경);
        Long 맥도날드_ID = restaurantDao.save(맥도날드);
        Long 교촌치킨_ID = restaurantDao.save(교촌치킨);
        Video 히밥_영상 = 영상(히밥_ID, 맥도날드_ID);
        Video 성시경_영상 = 영상(성시경_ID, 맥도날드_ID);
        videoDao.save(영상(히밥_ID, 교촌치킨_ID));
        videoDao.save(히밥_영상);
        videoDao.save(성시경_영상);
        List<FindAllVideoByRestaurantIdResponse> expected = List.of(
                toFindAllVideoByRestaurantIdResponse(히밥_영상, 히밥),
                toFindAllVideoByRestaurantIdResponse(성시경_영상, 성시경)
        );

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .queryParam("restaurantId", 맥도날드_ID)
                .when().get("/videos")
                .then().log().all()
                .extract();

        // then
        List<FindAllVideoByRestaurantIdResponse> responseBody = response.body().as(new TypeRef<>() {
        });
        assertThat(responseBody).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }
}
