package com.celuveat.celuveat.acceptance.celeb;

import static com.celuveat.celuveat.celeb.fixture.CelebFixture.성시경;
import static com.celuveat.celuveat.celeb.fixture.CelebFixture.히밥;
import static org.assertj.core.api.Assertions.assertThat;

import com.celuveat.celuveat.celeb.application.dto.FindAllCelebResponse;
import com.celuveat.celuveat.celeb.fixture.CelebFixture;
import com.celuveat.celuveat.celeb.infra.persistence.CelebDao;
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
@DisplayName("Celeb 인수테스트")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CelebAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CelebDao celebDao;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 모든_셀럽을_조회한다() {
        // given
        List<FindAllCelebResponse> expected = List.of(
                CelebFixture.toFindAllCelebResponse(성시경()),
                CelebFixture.toFindAllCelebResponse(히밥())
        );
        celebDao.save(성시경());
        celebDao.save(히밥());

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().get("/celebs")
                .then().log().all()
                .extract();

        // then
        List<FindAllCelebResponse> responseBody = response.body().as(new TypeRef<>() {
        });
        assertThat(responseBody).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }
}
