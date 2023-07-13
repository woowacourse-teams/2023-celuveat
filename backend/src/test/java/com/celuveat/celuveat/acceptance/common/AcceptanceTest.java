package com.celuveat.celuveat.acceptance.common;

import com.celuveat.celuveat.admin.domain.Admin;
import com.celuveat.celuveat.admin.infra.persistence.AdminDao;
import com.celuveat.celuveat.celeb.domain.Celeb;
import com.celuveat.celuveat.celeb.infra.persistence.CelebDao;
import com.celuveat.celuveat.restaurant.domain.Restaurant;
import com.celuveat.celuveat.restaurant.infra.persistence.RestaurantDao;
import com.celuveat.celuveat.video.domain.Video;
import com.celuveat.celuveat.video.domain.VideoHistory;
import com.celuveat.celuveat.video.infra.persistence.VideoDao;
import com.celuveat.celuveat.video.infra.persistence.VideoHistoryDao;
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
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    protected CelebDao celebDao;

    @Autowired
    protected RestaurantDao restaurantDao;

    @Autowired
    protected VideoDao videoDao;

    @Autowired
    protected AdminDao adminDao;

    @Autowired
    protected VideoHistoryDao videoHistoryDao;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    protected Long 셀럽을_저장한다(Celeb 셀럽) {
        return celebDao.save(셀럽);
    }

    protected Long 음식점을_저장한다(Restaurant 음식점) {
        return restaurantDao.save(음식점);
    }

    protected Long 영상을_저장한다(Video 영상) {
        return videoDao.save(영상);
    }

    protected Long 관리자를_저장한다(Admin 관리자) {
        return adminDao.save(관리자);
    }

    protected Long 영상_이력을_저장한다(VideoHistory 영상_이력) {
        return videoHistoryDao.save(영상_이력);
    }
}
