package com.celuveat.restaurant.query;

import com.celuveat.restaurant.query.dao.RestaurantReviewQueryResponseDao;
import com.celuveat.restaurant.query.dto.RestaurantReviewQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantReviewQueryService {

    private final RestaurantReviewQueryResponseDao restaurantReviewQueryResponseDao;

    public RestaurantReviewQueryResponse findAllByRestaurantId(Long restaurantId) {
        return restaurantReviewQueryResponseDao.findAllByRestaurantId(restaurantId);
    }
}
