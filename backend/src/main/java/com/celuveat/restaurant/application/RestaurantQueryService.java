package com.celuveat.restaurant.application;

import com.celuveat.restaurant.application.dto.RestaurantLikeQueryResponse;
import com.celuveat.restaurant.application.dto.RestaurantWithCelebAndImagesDetailResponse;
import com.celuveat.restaurant.application.dto.RestaurantWithCelebAndImagesSimpleResponse;
import com.celuveat.restaurant.application.mapper.RestaurantMapper;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantLike;
import com.celuveat.restaurant.domain.RestaurantLikeRepository;
import com.celuveat.restaurant.domain.RestaurantQueryRepository;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.LocationSearchCond;
import com.celuveat.restaurant.domain.RestaurantQueryRepository.RestaurantSearchCond;
import com.celuveat.restaurant.domain.RestaurantRepository;
import com.celuveat.restaurant.domain.dto.RestaurantWithDistance;
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

    private final RestaurantRepository restaurantRepository;
    private final RestaurantLikeRepository restaurantLikeRepository;
    private final RestaurantQueryRepository restaurantQueryRepository;
    private final RestaurantMapper restaurantMapper;

    public RestaurantWithCelebAndImagesDetailResponse findRestaurantDetailById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        return restaurantMapper.mapToRestaurantWithCelebAndImagesDetailResponse(restaurant);
    }

    public Page<RestaurantWithCelebAndImagesSimpleResponse> findAllWithMemberLiked(
            RestaurantSearchCond restaurantCond,
            LocationSearchCond locationCond,
            Pageable pageable,
            Long memberId
    ) {
        Page<RestaurantWithDistance> restaurantsWithDistance = restaurantQueryRepository.getRestaurantsWithDistance(
                restaurantCond, locationCond, pageable
        );
        return restaurantMapper.mapToRestaurantWithCelebAndImagesSimpleResponse(restaurantsWithDistance, memberId);
    }

    public Page<RestaurantWithCelebAndImagesSimpleResponse> findAllNearByDistanceWithoutSpecificRestaurant(
            int distance,
            long restaurantId,
            Pageable pageable
    ) {
        Page<RestaurantWithDistance> restaurantsWithDistance = restaurantQueryRepository.getRestaurantsNearByRestaurantId(
                distance, restaurantId, pageable
        );
        return restaurantMapper.mapToRestaurantWithCelebAndImagesSimpleResponse(restaurantsWithDistance, null);
    }

    public List<RestaurantLikeQueryResponse> findAllLikedRestaurantByMemberId(Long memberId) {
        List<RestaurantLike> restaurantLikes = restaurantLikeRepository.findAllByMemberId(memberId);
        return restaurantMapper.mapToRestaurantLikeQueryResponse(restaurantLikes);
    }
}
