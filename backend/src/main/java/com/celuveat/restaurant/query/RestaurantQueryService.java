package com.celuveat.restaurant.query;

import com.celuveat.restaurant.query.dao.RestaurantDetailResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantLikeQueryResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantSimpleResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.LocationSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantWithDistanceDao.RestaurantSearchCond;
import com.celuveat.restaurant.query.dto.RestaurantDetailResponse;
import com.celuveat.restaurant.query.dto.RestaurantLikeQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSimpleResponse;
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

    public RestaurantDetailResponse findRestaurantDetailById(Long restaurantId, @Nullable Long memberId) {
        return restaurantDetailResponseDao.findRestaurantDetailById(restaurantId, memberId);
    }

    public Page<RestaurantSimpleResponse> findAllWithMemberLiked(
            RestaurantSearchCond restaurantCond,
            LocationSearchCond locationCond,
            Pageable pageable,
            @Nullable Long memberId
    ) {
        return restaurantSimpleResponseDao.findAllWithMemberLiked(
                restaurantCond,
                locationCond,
                pageable,
                memberId
        );
    }

    public Page<RestaurantSimpleResponse> findAllNearByDistanceWithoutSpecificRestaurant(
            int distance,
            long restaurantId,
            @Nullable Long memberId,
            Pageable pageable
    ) {
        return restaurantSimpleResponseDao.findAllNearByDistanceWithoutSpecificRestaurant(
                distance,
                restaurantId,
                memberId,
                pageable
        );
    }

    public List<RestaurantLikeQueryResponse> findAllLikedRestaurantByMemberId(Long memberId) {
        return restaurantLikeQueryResponseDao.findAllLikedRestaurantByMemberId(memberId);
    }
}
