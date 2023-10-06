package com.celuveat.restaurant.query.dao;

import static java.util.stream.Collectors.toMap;

import com.celuveat.common.domain.BaseEntity;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewImage;
import com.celuveat.restaurant.query.dao.support.RestaurantReviewLikeQueryDaoSupport;
import com.celuveat.restaurant.query.dao.support.RestaurantReviewQueryDaoSupport;
import com.celuveat.restaurant.query.dto.RestaurantReviewQueryResponse;
import jakarta.annotation.Nullable;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantReviewQueryResponseDao {

    private final RestaurantReviewQueryDaoSupport restaurantReviewQueryDaoSupport;
    private final RestaurantReviewLikeQueryDaoSupport restaurantReviewLikeQueryDaoSupport;

    public RestaurantReviewQueryResponse findAllByRestaurantId(Long restaurantId, @Nullable Long memberId) {
        List<RestaurantReview> reviews =
                restaurantReviewQueryDaoSupport.findAllByRestaurantIdOrderByCreatedDateDesc(restaurantId);
        Map<Long, List<RestaurantReviewImage>> imagesGroupByReviewId = getImagesGroupByReviewId(reviews);
        Map<Long, Boolean> isLikedByReviewId = getReviewLikedGroupByReviewId(reviews, memberId);
        return RestaurantReviewQueryResponse.from(reviews, imagesGroupByReviewId, isLikedByReviewId);
    }

    private Map<Long, List<RestaurantReviewImage>> getImagesGroupByReviewId(List<RestaurantReview> reviews) {
        return reviews.stream()
                .collect(toMap(
                        BaseEntity::id,
                        RestaurantReview::images
                ));
    }

    private Map<Long, Boolean> getReviewLikedGroupByReviewId(
            List<RestaurantReview> reviews,
            Long memberId
    ) {
        return reviews.stream()
                .collect(toMap(
                        RestaurantReview::id,
                        review -> restaurantReviewLikeQueryDaoSupport
                                .findByRestaurantReviewAndMemberId(review, memberId)
                                .isPresent()
                ));
    }
}
