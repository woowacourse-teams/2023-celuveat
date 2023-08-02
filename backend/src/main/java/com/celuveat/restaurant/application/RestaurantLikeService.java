package com.celuveat.restaurant.application;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthMemberRepository;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantLike;
import com.celuveat.restaurant.domain.RestaurantLikeRepository;
import com.celuveat.restaurant.domain.RestaurantRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantLikeService {

    private final RestaurantRepository restaurantRepository;
    private final OauthMemberRepository oauthMemberRepository;
    private final RestaurantLikeRepository restaurantLikeRepository;

    public void like(Long restaurantId, Long memberId) {
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        OauthMember member = oauthMemberRepository.getById(memberId);
        Optional<RestaurantLike> restaurantLike =
                restaurantLikeRepository.findByRestaurantAndMember(restaurant, member);
        restaurantLike.ifPresentOrElse(
                restaurantLikeRepository::delete,
                () -> restaurantLikeRepository.save(new RestaurantLike(restaurant, member))
        );
    }
}
