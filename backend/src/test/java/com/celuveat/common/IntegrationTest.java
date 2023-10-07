package com.celuveat.common;

import com.celuveat.admin.command.application.AdminService;
import com.celuveat.auth.command.domain.OauthMemberRepository;
import com.celuveat.auth.command.domain.authcode.AuthCodeRequestUrlProviderComposite;
import com.celuveat.auth.query.OauthMemberQueryService;
import com.celuveat.celeb.command.domain.CelebRepository;
import com.celuveat.common.client.ImageUploadClient;
import com.celuveat.restaurant.command.application.RestaurantCorrectionService;
import com.celuveat.restaurant.command.application.RestaurantLikeService;
import com.celuveat.restaurant.command.application.RestaurantReviewLikeService;
import com.celuveat.restaurant.command.application.RestaurantReviewReportService;
import com.celuveat.restaurant.command.application.RestaurantReviewService;
import com.celuveat.restaurant.command.application.RestaurantService;
import com.celuveat.restaurant.command.domain.RestaurantImageRepository;
import com.celuveat.restaurant.command.domain.RestaurantLikeRepository;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.restaurant.command.domain.correction.RestaurantCorrectionRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewLikeRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewReportRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewRepository;
import com.celuveat.restaurant.query.RestaurantQueryService;
import com.celuveat.restaurant.query.RestaurantReviewQueryService;
import com.celuveat.restaurant.query.dao.RestaurantSearchQueryResponseDao;
import com.celuveat.restaurant.query.dao.support.RestaurantLikeQueryDaoSupport;
import com.celuveat.video.command.domain.VideoRepository;
import com.celuveat.video.query.VideoQueryService;
import com.celuveat.video.query.dao.VideoWithCelebQueryResponseDao;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Sql("/truncate.sql")
@DisplayNameGeneration(ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public abstract class IntegrationTest {

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
    protected AdminService adminService;
    @Autowired
    protected OauthMemberRepository oauthMemberRepository;
    @Autowired
    protected RestaurantLikeRepository restaurantLikeRepository;
    @Autowired
    protected RestaurantLikeQueryDaoSupport restaurantLikeQueryDaoSupport;
    @Autowired
    protected RestaurantReviewRepository restaurantReviewRepository;
    @Autowired
    protected OauthMemberQueryService oauthMemberQueryService;
    @Autowired
    protected AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;
    @Autowired
    protected VideoQueryService videoQueryService;
    @Autowired
    protected RestaurantService restaurantService;
    @Autowired
    protected RestaurantQueryService restaurantQueryService;
    @Autowired
    protected RestaurantLikeService likeService;
    @Autowired
    protected RestaurantReviewQueryService restaurantReviewQueryService;
    @Autowired
    protected RestaurantCorrectionService restaurantCorrectionService;
    @Autowired
    protected RestaurantLikeService restaurantLikeService;
    @Autowired
    protected RestaurantCorrectionRepository restaurantCorrectionRepository;
    @Autowired
    protected RestaurantReviewLikeService restaurantReviewLikeService;
    @Autowired
    protected RestaurantReviewLikeRepository restaurantReviewLikeRepository;
    @Autowired
    protected RestaurantReviewReportRepository restaurantReviewReportRepository;
    @Autowired
    protected RestaurantReviewReportService restaurantReviewReportService;
    @Autowired
    protected RestaurantReviewService restaurantReviewService;
    @Autowired
    protected VideoWithCelebQueryResponseDao videoWithCelebQueryResponseDao;
    @Autowired
    protected RestaurantSearchQueryResponseDao restaurantSearchQueryResponseDao;
    @Autowired
    protected SeedData seedData;
    @Autowired
    protected EntityManager em;
}
