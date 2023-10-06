package com.celuveat.restaurant.query;

import static com.celuveat.restaurant.query.mapper.RestaurantRelocator.relocateCelebDataFirstByCelebId;
import static com.celuveat.restaurant.query.mapper.RestaurantRelocator.relocateCelebDataFirstInResponsesByCelebId;

import com.celuveat.restaurant.query.dao.RestaurantByAddressResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantByAddressResponseDao.DistrictCodeCond;
import com.celuveat.restaurant.query.dao.RestaurantDetailResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantLikeQueryResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantSearchResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantSearchResponseDao.LocationSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantSearchResponseDao.RestaurantSearchCond;
import com.celuveat.restaurant.query.dto.LikedRestaurantQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantByAddressResponse;
import com.celuveat.restaurant.query.dto.RestaurantDetailResponse;
import com.celuveat.restaurant.query.dto.RestaurantSearchResponse;
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
    private final RestaurantSearchResponseDao restaurantSearchResponseDao;
    private final RestaurantLikeQueryResponseDao restaurantLikeQueryResponseDao;
    private final RestaurantByAddressResponseDao restaurantByAddressResponseDao;

    public RestaurantDetailResponse findRestaurantDetailById(
            Long restaurantId,
            Long celebId,
            @Nullable Long memberId
    ) {
        RestaurantDetailResponse response =
                restaurantDetailResponseDao.findRestaurantDetailById(restaurantId, memberId);
        return relocateCelebDataFirstByCelebId(celebId, response);
    }

    public Page<RestaurantSearchResponse> findAllWithMemberLiked(
            RestaurantSearchCond restaurantCond,
            LocationSearchCond locationCond,
            Pageable pageable,
            @Nullable Long memberId
    ) {
        Page<RestaurantSearchResponse> response = restaurantSearchResponseDao.findAll(
                restaurantCond, locationCond, pageable, memberId
        );
        Long celebId = restaurantCond.celebId();
        if (celebId == null) {
            return response;
        }
        return relocateCelebDataFirstInResponsesByCelebId(celebId, response);
    }

    public Page<RestaurantByAddressResponse> findAllByAddress(
            DistrictCodeCond cond,
            Pageable pageable,
            @Nullable Long memberId
    ) {
        return restaurantByAddressResponseDao.find(cond, pageable, memberId);
    }

    public Page<RestaurantSearchResponse> findAllNearByDistanceWithoutSpecificRestaurant(
            long restaurantId,
            int distance,
            Pageable pageable,
            @Nullable Long memberId
    ) {
        return restaurantSearchResponseDao.findNearBy(restaurantId, distance, pageable, memberId);
    }

    public List<LikedRestaurantQueryResponse> findAllLikedRestaurantByMemberId(Long memberId) {
        return restaurantLikeQueryResponseDao.findAllLikedRestaurantByMemberId(memberId);
    }

    public List<RestaurantSearchResponse> findLatest(@Nullable Long memberId) {
        return restaurantSearchResponseDao.findLatest(memberId);
    }
}
