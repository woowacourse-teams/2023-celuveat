package com.celuveat.restaurant.application;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthMemberRepository;
import com.celuveat.restaurant.application.dto.DeleteReviewCommand;
import com.celuveat.restaurant.application.dto.SaveReviewRequestCommand;
import com.celuveat.restaurant.application.dto.UpdateReviewRequestCommand;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantRepository;
import com.celuveat.restaurant.domain.review.RestaurantReview;
import com.celuveat.restaurant.domain.review.RestaurantReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantReviewService {

    private final RestaurantRepository restaurantRepository;
    private final OauthMemberRepository oauthMemberRepository;
    private final RestaurantReviewRepository restaurantReviewRepository;

    public Long create(SaveReviewRequestCommand command) {
        OauthMember member = oauthMemberRepository.getById(command.memberId());
        Restaurant restaurant = restaurantRepository.getById(command.restaurantId());
        RestaurantReview restaurantReview = new RestaurantReview(command.content(), member, restaurant);
        return restaurantReviewRepository.save(restaurantReview).id();
    }

    public void update(UpdateReviewRequestCommand command) {
        RestaurantReview review = restaurantReviewRepository.getById(command.reviewId());
        review.updateContent(command.content(), command.memberId(), command.restaurantId());
    }

    public void delete(DeleteReviewCommand command) {
        RestaurantReview review = restaurantReviewRepository.getById(command.reviewId());
        review.checkMemberAndRestaurantMatched(command.memberId(), command.restaurantId());
        restaurantReviewRepository.delete(review);
    }
}
