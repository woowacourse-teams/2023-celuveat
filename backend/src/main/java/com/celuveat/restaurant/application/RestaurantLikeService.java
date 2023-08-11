package com.celuveat.restaurant.application;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.auth.domain.OauthMemberRepository;
import com.celuveat.restaurant.domain.Restaurant;
import com.celuveat.restaurant.domain.RestaurantLike;
import com.celuveat.restaurant.domain.RestaurantLikeRepository;
import com.celuveat.restaurant.domain.RestaurantRepository;
import java.util.Optional;
import java.util.function.Consumer;
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
        Optional<RestaurantLike> like = restaurantLikeRepository.findByRestaurantAndMember(restaurant, member);
        like.ifPresentOrElse(cancelLike(), clickLike(restaurant, member));
    }

    private Consumer<RestaurantLike> cancelLike() {
        return restaurantLikeRepository::delete;
    }

    private Runnable clickLike(Restaurant restaurant, OauthMember member) {
        return () -> restaurantLikeRepository.save(new RestaurantLike(restaurant, member));
    }
}
