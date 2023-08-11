package com.celuveat.acceptance.common;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthMemberRepository;
import com.celuveat.celeb.domain.CelebRepository;
import com.celuveat.restaurant.application.RestaurantService;
import com.celuveat.restaurant.domain.RestaurantImageRepository;
import com.celuveat.restaurant.domain.RestaurantLikeRepository;
import com.celuveat.restaurant.domain.RestaurantRepository;
import com.celuveat.video.domain.VideoRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

@Sql("/truncate.sql")
@DisplayNameGeneration(ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

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

    protected void 멤버를_저장한다(OauthMember 멤버) {
        oauthMemberRepository.save(멤버);
    }
}
