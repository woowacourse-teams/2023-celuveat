package com.celuveat.restaurant.domain;

import com.celuveat.auth.domain.OauthMember;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantLikeRepository extends JpaRepository<RestaurantLike, Long> {

    Optional<RestaurantLike> findByRestaurantAndMember(Restaurant restaurant, OauthMember member);

    List<RestaurantLike> findAllByMember(OauthMember member);

    Integer countByRestaurant(Restaurant restaurant);
}
