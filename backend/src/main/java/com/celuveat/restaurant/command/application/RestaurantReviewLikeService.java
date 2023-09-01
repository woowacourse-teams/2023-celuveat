package com.celuveat.restaurant.command.application;

import static com.celuveat.restaurant.exception.RestaurantReviewExceptionType.CAN_NOT_LIKE_MY_REVIEW;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.OauthMemberRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewLike;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewLikeRepository;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewRepository;
import com.celuveat.restaurant.exception.RestaurantReviewException;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantReviewLikeService {

    private final RestaurantReviewRepository restaurantReviewRepository;
    private final OauthMemberRepository oauthMemberRepository;
    private final RestaurantReviewLikeRepository restaurantReviewLikeRepository;

    public void like(Long restaurantReviewId, Long memberId) {
        RestaurantReview restaurantReview = restaurantReviewRepository.getById(restaurantReviewId);
        OauthMember member = oauthMemberRepository.getById(memberId);
        if (restaurantReview.member().equals(member)) {
            throw new RestaurantReviewException(CAN_NOT_LIKE_MY_REVIEW);
        }
        restaurantReviewLikeRepository.findByRestaurantReviewAndMember(restaurantReview, member)
                .ifPresentOrElse(cancelLike(), clickLike(restaurantReview, member));
    }

    private Consumer<RestaurantReviewLike> cancelLike() {
        return restaurantReviewLikeRepository::delete;
    }

    private Runnable clickLike(RestaurantReview restaurantReview, OauthMember member) {
        return () -> restaurantReviewLikeRepository.save(new RestaurantReviewLike(restaurantReview, member));
    }
}
