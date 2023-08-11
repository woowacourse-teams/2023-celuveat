package com.celuveat.restaurant.application;

import com.celuveat.restaurant.application.dto.RestaurantDetailQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantQueryResponse;
import com.celuveat.restaurant.application.mapper.RestaurantMapper;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.LocationSearchCond;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.RestaurantSearchCond;
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
    public RestaurantDetailQueryResponse findRestaurantDetailById(Long restaurantId, Long celebId) {
        restaurantService.increaseViewCount(restaurantId);
        RestaurantDetailQueryResponse response = restaurantQueryService.findRestaurantDetailById(restaurantId);
        return RestaurantMapper.moveCelebDataFirstByCelebId(celebId, response);
    }

    @Transactional(readOnly = true)
    public Page<RestaurantQueryResponse> findAll(
            RestaurantSearchCond restaurantCond,
            LocationSearchCond locationCond,
            Pageable pageable,
            Optional<Long> memberId
    ) {
        Page<RestaurantQueryResponse> response = dispatchFindAll(restaurantCond, locationCond, pageable, memberId);
        if (restaurantCond.celebId() == null) {
            return response;
        }
        return RestaurantMapper.movedCelebDataFirstResponsesByCelebId(response, restaurantCond.celebId());
    }

    private Page<RestaurantQueryResponse> dispatchFindAll(
            RestaurantSearchCond restaurantCond,
            LocationSearchCond locationCond,
            Pageable pageable,
            Optional<Long> memberId
    ) {
        if (memberId.isEmpty()) {
            return restaurantQueryService.findAll(restaurantCond, locationCond, pageable);
        }
        return restaurantQueryService.findAllWithMemberLiked(restaurantCond, locationCond, pageable, memberId.get());
    }
}
