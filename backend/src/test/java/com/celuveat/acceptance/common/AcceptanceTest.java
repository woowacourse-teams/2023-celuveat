package com.celuveat.acceptance.common;

import static com.celuveat.acceptance.auth.OauthAcceptanceSteps.로그인을_요청한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.세션_아이디를_가져온다;
import static com.celuveat.auth.command.domain.OauthServerType.KAKAO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.when;

import com.celuveat.auth.command.application.OauthService;
import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.OauthMemberRepository;
import com.celuveat.celeb.command.domain.Celeb;
import com.celuveat.celeb.command.domain.CelebRepository;
import com.celuveat.celeb.fixture.CelebFixture;
import com.celuveat.common.SeedData;
import com.celuveat.common.client.ImageUploadClient;
import com.celuveat.restaurant.command.application.RestaurantService;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantImageRepository;
import com.celuveat.restaurant.command.domain.RestaurantLikeRepository;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewRepository;
import com.celuveat.video.command.domain.VideoRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

@Sql("/truncate.sql")
@DisplayNameGeneration(ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

    @MockBean
    protected OauthService oauthService;
    @MockBean
    protected ImageUploadClient imageUploadClient;
    @Autowired
    protected CelebRepository celebRepository;
    @Autowired
    protected RestaurantRepository restaurantRepository;
    @Autowired
    protected RestaurantImageRepository restaurantImageRepository;
    @Autowired
    protected VideoRepository videoRepository;
    @Autowired
    protected OauthMemberRepository oauthMemberRepository;
    @Autowired
    protected RestaurantLikeRepository restaurantLikeRepository;
    @Autowired
    protected RestaurantService restaurantService;
    @Autowired
    protected RestaurantReviewRepository restaurantReviewRepository;
    @Autowired
    protected Environment environment;
    @Autowired
    protected SeedData seedData;

    @LocalServerPort
    protected int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    protected ExtractableResponse<Response> 회원가입하고_로그인한다(OauthMember 멤버) {
        String authCode = "authCode";
        멤버를_저장한다(멤버);
        OAuth_응답을_설정한다(멤버, authCode);
        return 로그인을_요청한다(authCode);
    }

    protected String 회원가입하고_로그인하고_세션아이디를_가져온다(OauthMember 멤버) {
        var 로그인_결과 = 회원가입하고_로그인한다(멤버);
        return 세션_아이디를_가져온다(로그인_결과);
    }

    protected Celeb 셀럽을_저장한다(Celeb 셀럽) {
        return celebRepository.save(셀럽);
    }

    protected void 셀럽들을_저장한다(String... 셀럽들_이름) {
        List<Celeb> list = Arrays.stream(셀럽들_이름)
                .map(CelebFixture::셀럽)
                .toList();
        celebRepository.saveAll(list);
    }

    protected OauthMember 멤버를_저장한다(OauthMember 멤버) {
        return oauthMemberRepository.save(멤버);
    }

    protected Restaurant 음식점을_저장한다(Restaurant 음식점) {
        return restaurantRepository.save(음식점);
    }

    protected Long 음식점_리뷰를_저장한다(RestaurantReview 음식점_리뷰) {
        return restaurantReviewRepository.save(음식점_리뷰).id();
    }

    private void OAuth_응답을_설정한다(OauthMember member, String authCode) {
        when(oauthService.login(KAKAO, authCode)).thenReturn(member.id());
    }

    protected <T> void 데이터_수_검증(int expected, JpaRepository<T, Long> repository) {
        assertThat(repository.count()).isEqualTo(expected);
    }

    protected void 이미지_업로드를_설정한다(List<MultipartFile> images) {
        willDoNothing().given(imageUploadClient).upload(images);
    }
}
