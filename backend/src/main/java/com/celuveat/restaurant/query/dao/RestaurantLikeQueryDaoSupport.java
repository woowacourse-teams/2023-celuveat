package com.celuveat.restaurant.query.dao;

import com.celuveat.restaurant.command.domain.Restaurant;
import com.celuveat.restaurant.command.domain.RestaurantLike;
import com.celuveat.restaurant.query.dto.RestaurantIdWithLikeCount;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RestaurantLikeQueryDaoSupport extends JpaRepository<RestaurantLike, Long> {

    Optional<RestaurantLike> findByRestaurantAndMemberId(Restaurant restaurant, Long memberId);

    List<RestaurantLike> findAllByMemberId(Long memberId);

    List<RestaurantLike> findAllByMemberIdOrderByCreatedDateDesc(Long memberId);

    Integer countByRestaurant(Restaurant restaurant);

    @Query("""
            SELECT new com.celuveat.restaurant.query.dto.RestaurantIdWithLikeCount(
                rl.restaurant.id,
                count(rl)
            )
            FROM RestaurantLike rl
            WHERE rl.restaurant.id IN :restaurantIds
            GROUP BY rl.restaurant.id
            """)
    List<RestaurantIdWithLikeCount> likeCountGroupByRestaurantsId(@Param("restaurantIds") List<Long> restaurantIds);
}
