package com.celuveat.common;

import com.celuveat.administrativedistrict.TestAdministrativeDistrictRepository;
import com.celuveat.auth.command.domain.OauthMemberRepository;
import com.celuveat.celeb.command.domain.CelebRepository;
import com.celuveat.restaurant.command.domain.RestaurantImageRepository;
import com.celuveat.restaurant.command.domain.RestaurantLikeRepository;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewLikeRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewRepository;
import com.celuveat.video.command.domain.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@ActiveProfiles("test")
@RequiredArgsConstructor
public class TestDataInserter {

    private final OauthMemberRepository oauthMemberRepository;
    private final CelebRepository celebRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantImageRepository restaurantImageRepository;
    private final VideoRepository videoRepository;
    private final RestaurantLikeRepository restaurantLikeRepository;
    private final RestaurantReviewRepository restaurantReviewRepository;
    private final RestaurantReviewLikeRepository restaurantReviewLikeRepository;
    private final TestAdministrativeDistrictRepository administrativeDistrictRepository;

    public TestData insertData(TestDataCreator testDataCreator) {
        TestData testData = testDataCreator.create();
        return insertData(testData);
    }

    public TestData insertData(TestData testData) {
        oauthMemberRepository.saveAll(testData.members());
        celebRepository.saveAll(testData.celebs());
        restaurantRepository.saveAll(testData.restaurants());
        restaurantImageRepository.saveAll(testData.restaurantImages());
        videoRepository.saveAll(testData.videos());
        restaurantLikeRepository.saveAll(testData.restaurantLikes());
        restaurantReviewRepository.saveAll(testData.restaurantReviews());
        restaurantReviewLikeRepository.saveAll(testData.restaurantReviewLikes());
        administrativeDistrictRepository.saveAll(testData.administrativeDistricts());
        return testData;
    }
}
