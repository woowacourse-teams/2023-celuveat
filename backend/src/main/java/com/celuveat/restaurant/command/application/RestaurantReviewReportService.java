package com.celuveat.restaurant.command.application;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.OauthMemberRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewReport;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewReportRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantReviewReportService {

    private final RestaurantReviewReportRepository restaurantReviewReportRepository;
    private final RestaurantReviewRepository restaurantReviewRepository;
    private final OauthMemberRepository oauthMemberRepository;

    public void report(String content, Long restaurantReviewId, Long memberId) {
        RestaurantReview restaurantReview = restaurantReviewRepository.getById(restaurantReviewId);
        OauthMember member = oauthMemberRepository.getById(memberId);
        RestaurantReviewReport restaurantReviewReport = new RestaurantReviewReport(content, restaurantReview, member);
        restaurantReviewReportRepository.save(restaurantReviewReport);
    }
}
