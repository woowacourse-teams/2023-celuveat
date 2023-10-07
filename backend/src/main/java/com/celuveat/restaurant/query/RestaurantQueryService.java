package com.celuveat.restaurant.query;

import static com.celuveat.restaurant.query.mapper.RestaurantRelocator.relocateCelebDataFirstByCelebId;
import static com.celuveat.restaurant.query.mapper.RestaurantRelocator.relocateCelebDataFirstInResponsesByCelebId;

import com.celuveat.restaurant.query.dao.RestaurantByRegionCodeResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantByRegionCodeResponseDao.RegionCodeCond;
import com.celuveat.restaurant.query.dao.RestaurantDetailResponseDao;
import com.celuveat.restaurant.query.dao.LikedRestaurantQueryResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantSearchResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantSearchResponseDao.LocationSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantSearchResponseDao.RestaurantSearchCond;
import com.celuveat.restaurant.query.dto.LikedRestaurantQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantByRegionCodeResponse;
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
    private final LikedRestaurantQueryResponseDao likedRestaurantQueryResponseDao;
    private final RestaurantByRegionCodeResponseDao restaurantByRegionCodeResponseDao;

    public RestaurantDetailResponse findRestaurantDetailById(
            Long restaurantId,
            Long celebId,
            @Nullable Long memberId
    ) {
        RestaurantDetailResponse response =
                restaurantDetailResponseDao.find(restaurantId, memberId);
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

    public Page<RestaurantByRegionCodeResponse> findAllByRegionCode(
            RegionCodeCond cond,
            Pageable pageable,
            @Nullable Long memberId
    ) {
        return restaurantByRegionCodeResponseDao.find(cond, pageable, memberId);
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
        return likedRestaurantQueryResponseDao.findAllLikedRestaurantByMemberId(memberId);
    }

    public List<RestaurantSearchResponse> findLatest(@Nullable Long memberId) {
        return restaurantSearchResponseDao.findLatest(memberId);
    }
}
