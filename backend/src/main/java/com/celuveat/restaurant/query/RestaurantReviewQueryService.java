package com.celuveat.restaurant.query;

import com.celuveat.restaurant.query.dao.RestaurantReviewsQueryResponseDao;
import com.celuveat.restaurant.query.dto.RestaurantReviewsQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantReviewQueryService {

    private final RestaurantReviewsQueryResponseDao restaurantReviewsQueryResponseDao;

    public RestaurantReviewsQueryResponse findAllByRestaurantId(Long restaurantId, Long memberId) {
        return restaurantReviewsQueryResponseDao.findByRestaurantId(restaurantId, memberId);
    }
}
