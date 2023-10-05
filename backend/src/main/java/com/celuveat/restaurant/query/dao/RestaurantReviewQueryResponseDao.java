package com.celuveat.restaurant.query.dao;

import static com.celuveat.common.util.StreamUtil.groupBySameOrder;

import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewImage;
import com.celuveat.restaurant.query.dao.support.RestaurantReviewImageQueryDaoSupport;
import com.celuveat.restaurant.query.dao.support.RestaurantReviewLikeQueryDaoSupport;
import com.celuveat.restaurant.query.dao.support.RestaurantReviewQueryDaoSupport;
import com.celuveat.restaurant.query.dto.RestaurantReviewQueryResponse;
import jakarta.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantReviewQueryResponseDao {

    private final RestaurantReviewQueryDaoSupport restaurantReviewQueryDaoSupport;
    private final RestaurantReviewLikeQueryDaoSupport restaurantReviewLikeQueryDaoSupport;
    private final RestaurantReviewImageQueryDaoSupport restaurantReviewImageQueryDaoSupport;

    public RestaurantReviewQueryResponse findAllByRestaurantId(Long restaurantId, @Nullable Long memberId) {
        List<RestaurantReview> reviews =
                restaurantReviewQueryDaoSupport.findAllByRestaurantIdOrderByCreatedDateDesc(restaurantId);
        Map<Long, List<RestaurantReviewImage>> imagesGroupByReviewId = getImagesGroupByReviewId(reviews);
        Map<Long, Boolean> isLikedByReviewId = getReviewLikedGroupByReviewId(reviews, memberId);
        return RestaurantReviewQueryResponse.from(reviews, imagesGroupByReviewId, isLikedByReviewId);
    }

    private Map<Long, List<RestaurantReviewImage>> getImagesGroupByReviewId(List<RestaurantReview> reviews) {
        List<RestaurantReviewImage> restaurantReviewImages
                = restaurantReviewImageQueryDaoSupport.findAllByRestaurantReviewIn(reviews);
        return groupBySameOrder(restaurantReviewImages, image -> image.restaurantReview().id());
    }

    private Map<Long, Boolean> getReviewLikedGroupByReviewId(
            List<RestaurantReview> reviews,
            Long memberId
    ) {
        return reviews.stream().collect(Collectors.toMap(
                RestaurantReview::id,
                review -> restaurantReviewLikeQueryDaoSupport.findByRestaurantReviewAndMemberId(review, memberId)
                        .isPresent()
        ));
    }
}
