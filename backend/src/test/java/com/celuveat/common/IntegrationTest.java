package com.celuveat.common;

import com.celuveat.admin.command.application.AdminService;
import com.celuveat.auth.command.domain.OauthMemberRepository;
import com.celuveat.auth.command.domain.authcode.AuthCodeRequestUrlProviderComposite;
import com.celuveat.celeb.command.domain.CelebRepository;
import com.celuveat.common.client.ImageUploadClient;
import com.celuveat.restaurant.command.application.RestaurantCorrectionService;
import com.celuveat.restaurant.command.application.RestaurantImageSuggestionService;
import com.celuveat.restaurant.command.application.RestaurantLikeService;
import com.celuveat.restaurant.command.application.RestaurantReviewLikeService;
import com.celuveat.restaurant.command.application.RestaurantReviewReportService;
import com.celuveat.restaurant.command.application.RestaurantReviewService;
import com.celuveat.restaurant.command.application.RestaurantService;
import com.celuveat.restaurant.command.domain.RestaurantImageRepository;
import com.celuveat.restaurant.command.domain.RestaurantImageSuggestionRepository;
import com.celuveat.restaurant.command.domain.RestaurantLikeRepository;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.restaurant.command.domain.correction.RestaurantCorrectionRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewLikeRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewReportRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewRepository;
import com.celuveat.video.command.domain.VideoRepository;
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
    protected RestaurantReviewRepository restaurantReviewRepository;

    @Autowired
    protected RestaurantImageSuggestionRepository restaurantImageSuggestionRepository;

    @Autowired
    protected AuthCodeRequestUrlProviderComposite authCodeRequestUrlProviderComposite;

    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    protected RestaurantLikeService likeService;

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
    protected RestaurantImageSuggestionService restaurantImageSuggestionService;

    @Autowired
    protected RestaurantReviewService restaurantReviewService;

    @Autowired
    protected EntityManager em;
}
