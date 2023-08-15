package com.celuveat.restaurant.application;

import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.PERMISSION_DENIED;
import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.RESTAURANT_REVIEW_MISMATCH;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthMemberRepository;
import com.celuveat.restaurant.application.dto.SaveReviewRequestCommand;
import com.celuveat.restaurant.application.dto.UpdateReviewRequest;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantRepository;
import com.celuveat.restaurant.domain.review.RestaurantReview;
import com.celuveat.restaurant.domain.review.RestaurantReviewRepository;
import com.celuveat.restaurant.exception.RestaurantReviewException;
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

    public void updateReview(UpdateReviewRequest request, Long reviewId, Long memberId, Long restaurantId) {
        RestaurantReview review = restaurantReviewRepository.getById(reviewId);
        validateRequest(review, memberId, restaurantId);
        review.updateContent(request.content());
    }

    private void validateRequest(RestaurantReview review, Long memberId, Long restaurantId) {
        checkReviewAndMemberMatched(review, memberId);
        checkReviewAndRestaurantMatched(review, restaurantId);
    }

    private void checkReviewAndMemberMatched(RestaurantReview review, Long memberId) {
        Long reviewOwnerId = review.oauthMember().id();
        if (!reviewOwnerId.equals(memberId)) {
            throw new RestaurantReviewException(PERMISSION_DENIED);
        }
    }

    private void checkReviewAndRestaurantMatched(RestaurantReview review, Long restaurantId) {
        Long actualRestaurantId = review.restaurant().id();
        if (!actualRestaurantId.equals(restaurantId)) {
            throw new RestaurantReviewException(RESTAURANT_REVIEW_MISMATCH);
        }
    }

    public void deleteReview(Long reviewId, Long memberId, Long restaurantId) {
        RestaurantReview review = restaurantReviewRepository.getById(reviewId);
        validateRequest(review, memberId, restaurantId);
        restaurantReviewRepository.delete(review);
    }
}
