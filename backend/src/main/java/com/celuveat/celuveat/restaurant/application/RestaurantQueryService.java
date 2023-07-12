package com.celuveat.celuveat.restaurant.application;

import com.celuveat.celuveat.common.page.PageCond;
import com.celuveat.celuveat.common.page.SliceResponse;
import com.celuveat.celuveat.restaurant.application.dto.RestaurantSearchResponse;
import com.celuveat.celuveat.restaurant.domain.Restaurant;
import com.celuveat.celuveat.restaurant.infra.persistence.RestaurantDao;
import com.celuveat.celuveat.restaurant.infra.persistence.RestaurantQueryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantQueryService {

    private final RestaurantDao restaurantDao;
    private final RestaurantQueryDao restaurantQueryDao;

    public Restaurant findById(Long id) {
        return restaurantDao.getById(id);
    }

    public SliceResponse<RestaurantSearchResponse> findAllByCelebId(Long celebId, PageCond cond) {
        return restaurantQueryDao.findAllByCelebId(celebId, cond);
    }
}
