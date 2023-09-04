package com.celuveat.restaurant.query;

import com.celuveat.restaurant.command.application.RestaurantService;
import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.LocationSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.RestaurantSearchCond;
import com.celuveat.restaurant.query.dto.RestaurantDetailResponse;
import com.celuveat.restaurant.query.dto.RestaurantSimpleResponse;
import com.celuveat.restaurant.query.mapper.RestaurantRelocator;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantQueryFacade {

    private final RestaurantService restaurantService;
    private final RestaurantQueryService restaurantQueryService;

    @Transactional
    public RestaurantDetailResponse findRestaurantDetailById(
            Long restaurantId,
            Long celebId,
            Optional<Long> memberId
    ) {
        restaurantService.increaseViewCount(restaurantId);
        RestaurantDetailResponse response = restaurantQueryService.findRestaurantDetailById(restaurantId, memberId);
        return RestaurantRelocator.relocateCelebDataFirstByCelebId(celebId, response);
    }

    @Transactional(readOnly = true)
    public Page<RestaurantSimpleResponse> findAll(
            RestaurantSearchCond restaurantCond,
            LocationSearchCond locationCond,
            Pageable pageable,
            Optional<Long> memberId
    ) {
        Page<RestaurantSimpleResponse> response = restaurantQueryService.findAllWithMemberLiked(
                restaurantCond, locationCond, pageable, memberId.orElse(null)
        );
        Long celebId = restaurantCond.celebId();
        if (celebId == null) {
            return response;
        }
        return RestaurantRelocator.relocateCelebDataFirstInResponsesByCelebId(celebId, response);
    }
}
