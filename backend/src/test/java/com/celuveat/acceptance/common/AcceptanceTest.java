package com.celuveat.acceptance.common;

import static com.celuveat.acceptance.common.AcceptanceSteps.로그인을_요청한다;
import static com.celuveat.acceptance.common.AcceptanceSteps.세션_아이디를_가져온다;
import static com.celuveat.auth.domain.OauthServerType.KAKAO;

import com.celuveat.auth.application.OauthService;
import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthMemberRepository;
import com.celuveat.celeb.domain.CelebRepository;
import com.celuveat.restaurant.application.RestaurantService;
import com.celuveat.restaurant.domain.RestaurantImageRepository;
import com.celuveat.restaurant.domain.RestaurantLikeRepository;
import com.celuveat.restaurant.domain.RestaurantRepository;
import com.celuveat.video.domain.VideoRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@Sql("/truncate.sql")
@DisplayNameGeneration(ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

    @MockBean
    protected OauthService oauthService;

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

    private void 멤버를_저장한다(OauthMember 멤버) {
        oauthMemberRepository.save(멤버);
    }

    private void OAuth_응답을_설정한다(OauthMember member) {
        Mockito.when(oauthService.login(KAKAO, "abcd")).thenReturn(member.id());
    }
}
