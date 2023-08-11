package com.celuveat.restaurant.domain;

import com.celuveat.auth.domain.OauthMember;
import com.celuveat.restaurant.domain.dto.RestaurantIdWithCount;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RestaurantLikeRepository extends JpaRepository<RestaurantLike, Long> {

    Optional<RestaurantLike> findByRestaurantAndMember(Restaurant restaurant, OauthMember member);

    List<RestaurantLike> findAllByMember(OauthMember member);

    Integer countByRestaurant(Restaurant restaurant);

    @Query("""
            SELECT new com.celuveat.restaurant.domain.dto.RestaurantIdWithCount(
                rl.restaurant.id,
                count(rl)
            )
            FROM RestaurantLike rl
            WHERE rl.restaurant.id IN :restaurantIds
            GROUP BY rl.restaurant.id
            """)
    List<RestaurantIdWithCount> countLikesByRestaurantIds(@Param("restaurantIds") List<Long> restaurantIds);
}
