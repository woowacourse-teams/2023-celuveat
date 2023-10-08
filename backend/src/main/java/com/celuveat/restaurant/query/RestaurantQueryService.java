package com.celuveat.restaurant.query;

import static com.celuveat.restaurant.query.mapper.RestaurantRelocator.relocateCelebDataFirstByCelebId;
import static com.celuveat.restaurant.query.mapper.RestaurantRelocator.relocateCelebDataFirstInResponsesByCelebId;

import com.celuveat.restaurant.query.dao.RestaurantSearchWithoutDistanceQueryResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantSearchWithoutDistanceQueryResponseDao.RegionCodeCond;
import com.celuveat.restaurant.query.dao.RestaurantDetailQueryResponseDao;
import com.celuveat.restaurant.query.dao.LikedRestaurantQueryResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantSearchQueryResponseDao;
import com.celuveat.restaurant.query.dao.RestaurantSearchQueryResponseDao.LocationSearchCond;
import com.celuveat.restaurant.query.dao.RestaurantSearchQueryResponseDao.RestaurantSearchCond;
import com.celuveat.restaurant.query.dto.LikedRestaurantQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSearchWithoutDistanceResponse;
import com.celuveat.restaurant.query.dto.RestaurantDetailQueryResponse;
import com.celuveat.restaurant.query.dto.RestaurantSearchQueryResponse;
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

    private final RestaurantDetailQueryResponseDao restaurantDetailQueryResponseDao;
    private final RestaurantSearchQueryResponseDao restaurantSearchQueryResponseDao;
    private final LikedRestaurantQueryResponseDao likedRestaurantQueryResponseDao;
    private final RestaurantSearchWithoutDistanceQueryResponseDao restaurantSearchWithoutDistanceQueryResponseDao;

    public RestaurantDetailQueryResponse findRestaurantDetailById(
            Long restaurantId,
            Long celebId,
            @Nullable Long memberId
    ) {
        RestaurantDetailQueryResponse response =
                restaurantDetailQueryResponseDao.find(restaurantId, memberId);
        return relocateCelebDataFirstByCelebId(celebId, response);
    }

    public Page<RestaurantSearchQueryResponse> findAllWithMemberLiked(
            RestaurantSearchCond restaurantCond,
            LocationSearchCond locationCond,
            Pageable pageable,
            @Nullable Long memberId
    ) {
        Page<RestaurantSearchQueryResponse> response = restaurantSearchQueryResponseDao.findAll(
                restaurantCond, locationCond, pageable, memberId
        );
        Long celebId = restaurantCond.celebId();
        if (celebId == null) {
            return response;
        }
        return relocateCelebDataFirstInResponsesByCelebId(celebId, response);
    }

    public Page<RestaurantSearchWithoutDistanceResponse> findAllByRegionCode(
            RegionCodeCond cond,
            Pageable pageable,
            @Nullable Long memberId
    ) {
        return restaurantSearchWithoutDistanceQueryResponseDao.findByRegionCode(cond, pageable, memberId);
    }

    public Page<RestaurantSearchQueryResponse> findAllNearByDistanceWithoutSpecificRestaurant(
            long restaurantId,
            int distance,
            Pageable pageable,
            @Nullable Long memberId
    ) {
        return restaurantSearchQueryResponseDao.findNearBy(restaurantId, distance, pageable, memberId);
    }

    public List<LikedRestaurantQueryResponse> findAllLikedRestaurantByMemberId(Long memberId) {
        return likedRestaurantQueryResponseDao.findAllLikedRestaurantByMemberId(memberId);
    }

    public List<RestaurantSearchQueryResponse> findLatest(@Nullable Long memberId) {
        return restaurantSearchQueryResponseDao.findLatest(memberId);
    }
}
