package com.celuveat.restaurant.query;

import com.celuveat.restaurant.command.domain.review.RestaurantReview;
import com.celuveat.restaurant.command.domain.review.RestaurantReviewRepository;
import com.celuveat.restaurant.query.dto.RestaurantReviewQueryResponse;
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
