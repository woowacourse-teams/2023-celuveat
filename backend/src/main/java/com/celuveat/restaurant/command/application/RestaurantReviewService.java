package com.celuveat.restaurant.command.application;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.OauthMemberRepository;
import com.celuveat.restaurant.command.application.dto.DeleteReviewCommand;
import com.celuveat.restaurant.command.application.dto.SaveReviewRequestCommand;
import com.celuveat.restaurant.command.application.dto.UpdateReviewRequestCommand;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewLikeRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewRepository;
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
    private final RestaurantReviewLikeRepository restaurantReviewLikeRepository;

    public Long create(SaveReviewRequestCommand command) {
        OauthMember member = oauthMemberRepository.getById(command.memberId());
        Restaurant restaurant = restaurantRepository.getById(command.restaurantId());
        RestaurantReview restaurantReview =
                RestaurantReview.create(command.content(), member, restaurant, command.rating(), command.images());
        return restaurantReviewRepository.save(restaurantReview).id();
    }

    public void update(UpdateReviewRequestCommand command) {
        RestaurantReview review = restaurantReviewRepository.getById(command.reviewId());
        review.update(command.content(), command.memberId(), command.rating());
    }

    public void delete(DeleteReviewCommand command) {
        RestaurantReview review = restaurantReviewRepository.getById(command.reviewId());
        review.delete(command.memberId());
        restaurantReviewLikeRepository.deleteAllByRestaurantReview(review);
        restaurantReviewRepository.delete(review);
    }
}
