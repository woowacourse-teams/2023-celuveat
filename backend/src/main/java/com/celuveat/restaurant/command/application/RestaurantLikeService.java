package com.celuveat.restaurant.command.application;

import com.celuveat.auth.command.domain.OauthMember;
import com.celuveat.auth.command.domain.OauthMemberRepository;
import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import com.celuveat.restaurant.command.domain.RestaurantLikeRepository;
import com.celuveat.restaurant.command.domain.RestaurantRepository;
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
        restaurantLikeRepository.findByRestaurantAndMember(restaurant, member)
                .ifPresentOrElse(this::cancelLike, clickLike(restaurant, member));
    }

    private void cancelLike(RestaurantLike like) {
        like.cancel();
        restaurantLikeRepository.delete(like);
    }

    private Runnable clickLike(Restaurant restaurant, OauthMember member) {
        RestaurantLike like = RestaurantLike.create(restaurant, member);
        return () -> restaurantLikeRepository.save(like);
    }
}
