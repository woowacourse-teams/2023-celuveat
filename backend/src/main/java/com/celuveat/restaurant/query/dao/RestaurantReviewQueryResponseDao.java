package com.celuveat.restaurant.query.dao;

import static com.celuveat.common.util.StreamUtil.groupBySameOrder;

import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewImage;
import com.celuveat.restaurant.query.dao.support.RestaurantReviewImageQueryDaoSupport;
import com.celuveat.restaurant.query.dao.support.RestaurantReviewQueryDaoSupport;
import com.celuveat.restaurant.query.dto.RestaurantReviewQueryResponse;
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
    private final RestaurantReviewImageQueryDaoSupport restaurantReviewImageQueryDaoSupport;

    public RestaurantReviewQueryResponse findAllByRestaurantId(Long restaurantId) {
        List<RestaurantReview> reviews =
                restaurantReviewQueryDaoSupport.findAllByRestaurantIdOrderByCreatedDateDesc(restaurantId);
        Map<Long, List<RestaurantReviewImage>> imagesGroupByReviewId = getImagesGroupByReviewId(reviews);
        return RestaurantReviewQueryResponse.from(reviews, imagesGroupByReviewId);
    }

    private Map<Long, List<RestaurantReviewImage>> getImagesGroupByReviewId(List<RestaurantReview> reviews) {
        List<RestaurantReviewImage> restaurantReviewImages
                = restaurantReviewImageQueryDaoSupport.findAllByRestaurantReviewIn(reviews);
        return groupBySameOrder(restaurantReviewImages, image -> image.restaurantReview().id());
    }
}
