package com.celuveat.restaurant.query;

import com.celuveat.restaurant.query.dao.RestaurantDetailResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantLikeQueryResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantSimpleResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.LocationSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.RestaurantSearchCond;
import com.celuveat.restaurant.query.dto.LikedRestaurantQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantDetailResponse;
import com.celuveat.restaurant.query.dto.RestaurantSimpleResponse;
import com.celuveat.restaurant.query.mapper.RestaurantRelocator;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantQueryService {

    private final RestaurantDetailResponseDao restaurantDetailResponseDao;
    private final RestaurantSimpleResponseDao restaurantSimpleResponseDao;
    private final RestaurantLikeQueryResponseDao restaurantLikeQueryResponseDao;

    public RestaurantDetailResponse findRestaurantDetailById(
            Long restaurantId,
            Long celebId,
            @Nullable Long memberId
    ) {
        RestaurantDetailResponse response =
                restaurantDetailResponseDao.findRestaurantDetailById(restaurantId, memberId);
        return RestaurantRelocator.relocateCelebDataFirstByCelebId(celebId, response);
    }

    public Page<RestaurantSimpleResponse> findAllWithMemberLiked(
            RestaurantSearchCond restaurantCond,
            LocationSearchCond locationCond,
            Pageable pageable,
            @Nullable Long memberId
    ) {
        Page<RestaurantSimpleResponse> response = restaurantSimpleResponseDao.findAllWithMemberLiked(
                restaurantCond,
                locationCond,
                pageable,
                memberId
        );
        Long celebId = restaurantCond.celebId();
        if (celebId == null) {
            return response;
        }
        return RestaurantRelocator.relocateCelebDataFirstInResponsesByCelebId(celebId, response);
    }

    public Page<RestaurantSimpleResponse> findAllNearByDistanceWithoutSpecificRestaurant(
            long restaurantId,
            int distance,
            @Nullable Long memberId,
            Pageable pageable
    ) {
        return restaurantSimpleResponseDao.findAllNearByDistanceWithoutSpecificRestaurant(
                restaurantId,
                distance,
                memberId,
                pageable
        );
    }

    public List<LikedRestaurantQueryResponse> findAllLikedRestaurantByMemberId(Long memberId) {
        return restaurantLikeQueryResponseDao.findAllLikedRestaurantByMemberId(memberId);
    }
}
