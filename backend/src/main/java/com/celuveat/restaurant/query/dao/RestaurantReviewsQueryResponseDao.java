package com.celuveat.restaurant.query.dao;

import static java.util.stream.Collectors.toMap;

import com.celuveat.common.dao.Dao;
import com.celuveat.common.domain.BaseEntity;
import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewImage;
import com.celuveat.restaurant.query.dao.support.RestaurantReviewLikeQueryDaoSupport;
import com.celuveat.restaurant.query.dao.support.RestaurantReviewQueryDaoSupport;
import com.celuveat.restaurant.query.dto.RestaurantReviewsQueryResponse;
import jakarta.annotation.Nullable;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Dao
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantReviewsQueryResponseDao {

    private final RestaurantReviewQueryDaoSupport restaurantReviewQueryDaoSupport;
    private final RestaurantReviewLikeQueryDaoSupport restaurantReviewLikeQueryDaoSupport;

    public RestaurantReviewsQueryResponse findByRestaurantId(Long restaurantId, @Nullable Long memberId) {
        List<RestaurantReview> reviews =
                restaurantReviewQueryDaoSupport.findAllByRestaurantIdOrderByCreatedDateDesc(restaurantId);
        Map<Long, List<RestaurantReviewImage>> imagesGroupByReviewId = getImagesGroupByReviewId(reviews);
        Map<Long, Boolean> isLikedByReviewId = getReviewLikedGroupByReviewId(reviews, memberId);
        return RestaurantReviewsQueryResponse.from(reviews, imagesGroupByReviewId, isLikedByReviewId);
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
