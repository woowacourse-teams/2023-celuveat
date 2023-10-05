package com.celuveat.restaurant.command.application;

import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.CAN_NOT_LIKE_MY_REVIEW;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.OauthMemberRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewLike;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewLikeRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewRepository;
import com.celuveat.restaurant.exception.RestaurantReviewException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantReviewLikeService {

    private final OauthMemberRepository oauthMemberRepository;
    private final RestaurantReviewRepository restaurantReviewRepository;
    private final RestaurantReviewLikeRepository restaurantReviewLikeRepository;

    public void like(Long restaurantReviewId, Long memberId) {
        RestaurantReview restaurantReview = restaurantReviewRepository.getById(restaurantReviewId);
        OauthMember member = oauthMemberRepository.getById(memberId);
        if (restaurantReview.member().equals(member)) {
            throw new RestaurantReviewException(CAN_NOT_LIKE_MY_REVIEW);
        }
        Optional<RestaurantReviewLike> likeHistory =
                restaurantReviewLikeRepository.findByRestaurantReviewAndMember(restaurantReview, member);
        if (likeHistory.isPresent()) {
            cancelLike(restaurantReview, likeHistory.get());
            return;
        }
        clickLike(restaurantReview, member);
    }

    private void cancelLike(RestaurantReview restaurantReview, RestaurantReviewLike restaurantReviewLike) {
        restaurantReview.cancelLike();
        restaurantReviewLikeRepository.delete(restaurantReviewLike);
    }

    private void clickLike(RestaurantReview restaurantReview, OauthMember member) {
        restaurantReview.clickLike();
        restaurantReviewLikeRepository.save(new RestaurantReviewLike(restaurantReview, member));
    }
}
