package com.celuveat.restaurant.application;

import com.celuveat.restaurant.application.dto.RestaurantWithCelebAndImagesDetailResponse;
import com.celuveat.restaurant.application.dto.RestaurantWithCelebAndImagesSimpleResponse;
import com.celuveat.restaurant.application.mapper.RestaurantRelocator;
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
    public RestaurantWithCelebAndImagesDetailResponse findRestaurantDetailById(Long restaurantId, Long celebId) {
        restaurantService.increaseViewCount(restaurantId);
        RestaurantWithCelebAndImagesDetailResponse response = restaurantQueryService.findRestaurantDetailById(
                restaurantId
        );
        return RestaurantRelocator.relocateCelebDataFirstByCelebId(celebId, response);
    }

    @Transactional(readOnly = true)
    public Page<RestaurantWithCelebAndImagesSimpleResponse> findAll(
            RestaurantSearchCond restaurantCond,
            LocationSearchCond locationCond,
            Pageable pageable,
            Optional<Long> memberId
    ) {
        Page<RestaurantWithCelebAndImagesSimpleResponse> response = restaurantQueryService.findAllWithMemberLiked(
                restaurantCond, locationCond, pageable, memberId.orElse(null)
        );
        if (restaurantCond.celebId() == null) {
            return response;
        }
        return RestaurantRelocator.relocateCelebDataFirstResponsesByCelebId(response, restaurantCond.celebId());
    }
}
