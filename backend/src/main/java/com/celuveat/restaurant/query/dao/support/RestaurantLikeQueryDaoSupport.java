package com.celuveat.restaurant.query.dao.support;

import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RestaurantLikeQueryDaoSupport extends JpaRepository<RestaurantLike, Long> {

    Optional<RestaurantLike> findByRestaurantAndMemberId(Restaurant restaurant, Long memberId);

    @EntityGraph(attributePaths = "restaurant")
    List<RestaurantLike> findAllByMemberIdOrderByCreatedDateDesc(Long memberId);

    Integer countByRestaurant(Restaurant restaurant);

    @Query("SELECT rl FROM RestaurantLike rl WHERE rl.member.id = :memberId AND rl.restaurant.id IN :restaurantIds")
    List<RestaurantLike> findAllByMemberIdAndRestaurantIdsIn(
            @Param(("memberId")) Long memberId,
            @Param("restaurantIds") List<Long> restaurantIds
    );
}
