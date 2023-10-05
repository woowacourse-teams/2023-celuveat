package com.celuveat.acceptance.common;

import static com.celuveat.acceptance.common.AcceptanceSteps.로그인을_요청한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.세션_아이디를_가져온다;
import static com.celuveat.auth.command.domain.OauthServerType.KAKAO;

import com.celuveat.auth.command.application.OauthService;
import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.OauthMemberRepository;
import com.celuveat.celeb.command.domain.CelebRepository;
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
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
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
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    protected String 회원가입하고_로그인한다(OauthMember 멤버) {
        멤버를_저장한다(멤버);
        OAuth_응답을_설정한다(멤버);
        ExtractableResponse<Response> 로그인_응답 = 로그인을_요청한다();
        return 세션_아이디를_가져온다(로그인_응답);
    }

    protected OauthMember 멤버를_저장한다(OauthMember 멤버) {
        return oauthMemberRepository.save(멤버);
    }

    protected void 음식점을_저장한다(Restaurant 음식점) {
        restaurantRepository.save(음식점);
    }

    protected Long 음식점_리뷰를_저장한다(RestaurantReview 음식점_리뷰) {
        return restaurantReviewRepository.save(음식점_리뷰).id();
    }

    private void OAuth_응답을_설정한다(OauthMember member) {
        Mockito.when(oauthService.login(KAKAO, "abcd")).thenReturn(member.id());
    }

    protected void 이미지_업로드를_설정한다(List<MultipartFile> images) {
        BDDMockito.willDoNothing().given(imageUploadClient).upload(images);
    }
}
