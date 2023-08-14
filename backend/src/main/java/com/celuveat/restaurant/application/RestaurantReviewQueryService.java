package com.celuveat.restaurant.application;

import com.celuveat.restaurant.application.dto.RestaurantReviewQueryResponse;
import com.celuveat.restaurant.domain.review.RestaurantReview;
import com.celuveat.restaurant.domain.review.RestaurantReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantReviewQueryService {

    private final RestaurantReviewRepository restaurantReviewRepository;

    public RestaurantReviewQueryResponse findAllByRestaurantId(Long restaurantId) {
        List<RestaurantReview> reviews =
                restaurantReviewRepository.findAllByRestaurantIdOrderByCreatedDateDesc(restaurantId);
        return RestaurantReviewQueryResponse.from(reviews);
    }
}
